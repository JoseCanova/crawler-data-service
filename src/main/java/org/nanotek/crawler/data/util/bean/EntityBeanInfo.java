package org.nanotek.crawler.data.util.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import org.nanotek.beans.BeanInfo;
import org.nanotek.beans.PropertyDescriptor;
import org.nanotek.crawler.data.util.Introspector;


public class EntityBeanInfo<E> extends ClassInfo {

	private Class<E> entityClass;
	
	private static 
		Map<Class<?> , Map<String,PropertyDescriptor>> classDescriptorMap = 
										new HashMap<Class<?> , Map<String,PropertyDescriptor>>();
	
	private Map<String,PropertyDescriptor> propertyDescriptorInfo;
	
    private static Semaphore semaphore = new Semaphore(1);
	
	private static AtomicBoolean isConfigured = new AtomicBoolean(false);
	
	public boolean  isConfigured() {
			acquire();
			if((propertyDescriptorInfo =
						classDescriptorMap.get(this.entityClass))!=null) {
				isConfigured.set(true);
			}else { 
				isConfigured.set(false);
			}
			release();
		return isConfigured.get();
	}
	
	public Class<E> getEntityClass() {
		return entityClass;
	}

	public void setConfigured(boolean b) {
		isConfigured.set(b);
		release();
	}
	
	public void acquire() {
		try { 
			semaphore.acquire();
		}catch (Exception ex) { 
			isConfigured.set(false);
			throw new RuntimeException(ex);
		}
	}
	
	public void release() { 
		try { 
			semaphore.release();
		}catch (Exception ex) { 
			throw new RuntimeException(ex);
		}
	}
	
	public EntityBeanInfo(Class<E> entityClass) {
		super(entityClass);
		this.entityClass = entityClass;
		postContruct();
	}

	EntityBeanInfo <?> construcMethodInfoList(){
		this.methods = ClassInfo.get(entityClass).getMethods();
		return this;
	}
	
	private void postContruct() {
		if(!isConfigured()) { 
			acquire();
			propertyDescriptorInfo = new HashMap<String,PropertyDescriptor>();
			prepareChache(entityClass)
				.construcMethodInfoList()
				.contructPropertiesInfoList()
				.constructProperyDescriptorInfos();
			classDescriptorMap.put(entityClass, propertyDescriptorInfo);
			setConfigured(true);
		}
	}
	
	private void constructProperyDescriptorInfos() {
		try {
			BeanInfo binfo = Introspector.getBeanInfo(getEntityClass());
			Optional
						.ofNullable(binfo.getPropertyDescriptors())
						.ifPresent(ps -> {
							Stream
								.of(ps)
								.forEach(p->propertyDescriptorInfo.put(p.getName(), p));
						});
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1);
		}
	}

	private EntityBeanInfo <?> contructPropertiesInfoList() {
		this.properties = ClassInfo.get(entityClass).getProperties();
		return this;
	}

	public   EntityBeanInfo <?>  prepareChache(Class<?> type) {
		ClassInfo.get(type);
		return this;
	}
	
	public Map<String, PropertyDescriptor> getPropertyDescriptorInfo() {
		return propertyDescriptorInfo;
	}
	
}
