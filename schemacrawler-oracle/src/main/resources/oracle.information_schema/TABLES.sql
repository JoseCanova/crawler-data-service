SELECT
  NULL AS TABLE_CAT,
  TABLES.OWNER AS TABLE_SCHEM,
  TABLES.TABLE_NAME AS TABLE_NAME,
  TABLES.TABLE_TYPE AS TABLE_TYPE,
  TABLES.COMMENTS AS REMARKS
FROM
   ALL_TAB_COMMENTS TABLES
WHERE 
  TABLES.TABLE_NAME NOT LIKE 'BIN$%'
  AND NOT REGEXP_LIKE(TABLES.TABLE_NAME, '^(SYS_IOT|MDOS|MDRS|MDRT|MDOT|MDXT)_.*$')  
  AND TABLES.TABLE_NAME LIKE '%ESTRUTURA%'