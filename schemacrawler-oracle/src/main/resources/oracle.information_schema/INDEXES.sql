SELECT
  NULL AS TABLE_CAT,
  INDEXES.OWNER AS TABLE_SCHEM,
  INDEXES.TABLE_NAME,
  DECODE (INDEXES.UNIQUENESS, 'UNIQUE', 0, 1) AS NON_UNIQUE,
  NULL AS INDEX_QUALIFIER,
  INDEXES.INDEX_NAME,
  3 AS TYPE, -- JDBC index type "Other"
  INDEX_COLUMNS.COLUMN_POSITION AS ORDINAL_POSITION,
  INDEX_COLUMNS.COLUMN_NAME,
  CASE WHEN DESCEND = 'ASC' THEN 'A' ELSE 'D' END AS ASC_OR_DESC,
  INDEXES.DISTINCT_KEYS AS CARDINALITY,
  INDEXES.LEAF_BLOCKS AS PAGES,
  NULL AS FILTER_CONDITION
FROM
  ${catalogscope}_INDEXES INDEXES
  INNER JOIN ${catalogscope}_IND_COLUMNS INDEX_COLUMNS
  ON
    INDEXES.INDEX_NAME = INDEX_COLUMNS.INDEX_NAME
    AND INDEXES.TABLE_OWNER = INDEX_COLUMNS.TABLE_OWNER
    AND INDEXES.TABLE_NAME = INDEX_COLUMNS.TABLE_NAME
    AND INDEXES.OWNER = 'ADMACI'
WHERE
  REGEXP_LIKE(INDEXES.OWNER, 'ADMACI')
  AND INDEXES.TABLE_NAME NOT LIKE 'BIN$%'
  AND NOT REGEXP_LIKE(INDEXES.TABLE_NAME, '^(SYS_IOT|MDOS|MDRS|MDRT|MDOT|MDXT)_.*$')
ORDER BY
  TABLE_SCHEM,
  TABLE_NAME,
  INDEX_NAME,
  NON_UNIQUE,
  TYPE,
  ORDINAL_POSITION
