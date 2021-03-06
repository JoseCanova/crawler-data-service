package org.nanotek.crawler.data.util.bean;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import org.nanotek.beans.BeanInfo;
import org.nanotek.beans.PropertyDescriptor;
import org.nanotek.crawler.data.util.Introspector;




public interface MutatorSupport<T>{


	public static boolean isInstanceOf(Object bean, Class<?> targetType) {
		return Introspector.isSubclass(bean.getClass(), targetType);
	}

	default <Z> Optional<? super Z> getProperty(String propertyName) { 
		return getPropertyDescriptors(this.getClass())
				.map(ps->{
					PropertyDescriptor z = null;
					return Stream
							.of(ps)
							.reduce(z,(test , value)->{
								if(value.getName().equalsIgnoreCase(propertyName)) {
									test = value;
								}
								return test;
							});
				})
				.map(p->read(p.getReadMethod(), this));
	}

	

	public static <Z> Optional<? super Z> getProperty(String propertyName,Object instance) { 
		return getPropertyDescriptors(instance.getClass())
				.map(ps->{
					PropertyDescriptor z = null;
					return Stream
							.of(ps)
							.reduce(z,(test , value)->{
								if(value.getName().equals(propertyName)) {
									test = value;
								}
								return test;
							});
				})
				.map(p->read(p.getReadMethod(), instance));
	}
	
	//TODO: define a criteria verify parameters from readmethod
	public  static Object read(Method readMethod, Object instance) {
		try{
			return readMethod.invoke(instance, new Object[] {});
		}catch(Exception ex) { 
			throw new RuntimeException();
		}
	}
	
	//TODO: define a criteria verify parameters from readmethod
	default Object read(Method readMethod, MutatorSupport<T> mutatorSupport) {
		try{
			return readMethod.invoke(mutatorSupport, new Object[] {});
		}catch(Exception ex) { 
			throw new RuntimeException();
		}
	}

	public static Optional<PropertyDescriptor[]> getPropertyDescriptors(Class<?> type) { 
		try {
			EntityBeanInfo<?> beanInfo = new EntityBeanInfo<>(type);
			PropertyDescriptor[] desc = beanInfo.getPropertyDescriptorInfo().values()
											.toArray(new PropertyDescriptor[ beanInfo
											                                 .getPropertyDescriptorInfo().values().size()]);
			return Optional.of(desc);
		}catch (Exception ex) { 
			ex.printStackTrace();
		}
		return Optional.empty();
	}

	public static Optional<Collection<PropertyDescriptor>> getPropertyDescriptorsCollection(Class<?> type) { 
		try {
			EntityBeanInfo<?> beanInfo = new EntityBeanInfo<>(type);
			return Optional.ofNullable(beanInfo.getPropertyDescriptorInfo().values());
		}catch (Exception ex) { 
			ex.printStackTrace();
		}
		return Optional.empty();
	}
	
	/**
	 * use just for acessor/mutator interfaces.
	 * @param type
	 * @return
	 */
	public static Optional<PropertyDescriptor> getPropertyDescriptor(Class<?> type) { 
		try {
			PropertyDescriptor[] desc = MutatorSupport.getPropertyDescriptors(type).get();
			if (desc ==null || desc.length != 1) return Optional.empty();
			return Optional.of(desc[0]);
		}catch (Exception ex) { 
			ex.printStackTrace();
		}
		return Optional.empty();
	}

	public static <T> Optional<T> empty(){
		return Optional.empty();
	}
	/**
	 * Check if class is a property class (ie mutator acessor class).
	 * 
	 * @param type
	 * @return
	 */
	public static boolean isPropertyBean(Class<?> clazz) {
		try {	
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] desc = beanInfo.getPropertyDescriptors();
			if(desc == null || desc.length !=1) return false; 
			PropertyDescriptor descr=desc[0];
			return descr.getWriteMethod() !=null || descr.getReadMethod() !=null;
		}catch (Exception ex) { 
			ex.printStackTrace();
		}
		return false;
	}

}
