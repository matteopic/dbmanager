<?xml version="1.0" encoding="ISO-8859-1"?> 
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="text" indent="yes" />



	<xsl:template match="tabelle">
		<xsl:apply-templates/>
	</xsl:template>




	<xsl:template match="tabella">
#----------------------------
# Table structure for <xsl:value-of select="@nome"/>
#----------------------------
CREATE TABLE <xsl:value-of select="@nome"/>(
	<xsl:apply-templates />
);

	</xsl:template>




	<xsl:template match="colonne">
		<xsl:apply-templates />
	</xsl:template>




	<xsl:template match="colonna">
		<xsl:value-of select="@nome"/><xsl:text> </xsl:text>
		<xsl:apply-templates select="tipo"/>
		<xsl:apply-templates select="ammettiNull"/>
		<!--xsl:apply-templates select="primaryKey"/>
		<xsl:apply-templates select="defaultValue"/-->
		
		<!-- Finchè non raggiungo l'ultima colonna della tabella metto una virgola e vado accapo-->
		<xsl:if test="position()!=last()"><xsl:text>,
	</xsl:text>
		</xsl:if>
	</xsl:template>




	<xsl:template match="primaryKey">
		<xsl:choose>
		  <xsl:when test="text()='true'">
			<xsl:text> PRIMARY KEY</xsl:text>
		  </xsl:when>
		</xsl:choose>
	</xsl:template>
	
	
	

	<xsl:template match="dimensione">
		<xsl:text> (</xsl:text><xsl:value-of select="text()"/><xsl:text>) </xsl:text>	
	</xsl:template>



	<!-- 
		Mapping tra i valori di java.sql.Types e i tipi di dato del DB
		
		Modificare il valore all'interno dell'elemnto xsl:text con quello utilizzato dal database
		ad esempio se un database anzichè usare INTEGER utilizza INT allora 
		
		  <xsl:when test="text()='4'">
			<xsl:text>INT</xsl:text>
		  </xsl:when>
		  
		La cifra 4 rappresenta il valore di java.sql.Types.INTEGER
	-->
	<xsl:template match="tipo">
		<xsl:choose>
			<xsl:when test="text()='-7'">
               <xsl:text>bool</xsl:text> <xsl:apply-templates select="../dimensione"/>
           </xsl:when>

           <!--xsl:when test="text()='-7'">
               <xsl:text>bit</xsl:text>
           </xsl:when-->

           <xsl:when test="text()='-5'">
               <xsl:text>int8</xsl:text> <xsl:apply-templates select="../dimensione"/>
           </xsl:when>

           <!--xsl:when test="text()='-5'">
               <xsl:text>bigserial</xsl:text>
           </xsl:when-->

           <xsl:when test="text()='-2'">
               <xsl:text>bytea</xsl:text> <xsl:apply-templates select="../dimensione"/>
           </xsl:when>

           <xsl:when test="text()='1'">
               <xsl:text>bpchar</xsl:text> <xsl:apply-templates select="../dimensione"/>
           </xsl:when>

           <xsl:when test="text()='2'">
               <xsl:text>numeric</xsl:text> <xsl:apply-templates select="../dimensione"/>
           </xsl:when>

           <xsl:when test="text()='4'">
               <xsl:text>int4</xsl:text> <xsl:apply-templates select="../dimensione"/>
           </xsl:when>

           <!--xsl:when test="text()='4'">
               <xsl:text>serial</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='4'">
               <xsl:text>oid</xsl:text>
           </xsl:when-->

           <xsl:when test="text()='5'">
               <xsl:text>int2</xsl:text> <xsl:apply-templates select="../dimensione"/>
           </xsl:when>

           <xsl:when test="text()='7'">
               <xsl:text>float4</xsl:text> <xsl:apply-templates select="../dimensione"/>
           </xsl:when>

           <xsl:when test="text()='8'">
               <xsl:text>float8</xsl:text> <xsl:apply-templates select="../dimensione"/>
           </xsl:when>

           <!--xsl:when test="text()='8'">
               <xsl:text>money</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='12'">
               <xsl:text>name</xsl:text>
           </xsl:when-->

           <xsl:when test="text()='12'">
               <xsl:text>text</xsl:text> <xsl:apply-templates select="../dimensione"/>
           </xsl:when>

           <!--xsl:when test="text()='12'">
               <xsl:text>varchar</xsl:text>
           </xsl:when-->

           <xsl:when test="text()='91'">
               <xsl:text>date</xsl:text> <xsl:apply-templates select="../dimensione"/>
           </xsl:when>

           <xsl:when test="text()='92'">
               <xsl:text>time</xsl:text> <xsl:apply-templates select="../dimensione"/>
           </xsl:when>

           <!--xsl:when test="text()='92'">
               <xsl:text>timetz</xsl:text>
           </xsl:when-->

           <xsl:when test="text()='93'">
               <xsl:text>timestamp</xsl:text> <xsl:apply-templates select="../dimensione"/>
           </xsl:when>

           <!--xsl:when test="text()='93'">
               <xsl:text>timestamptz</xsl:text>
           </xsl:when-->

			<!-- OTHER -->
           <xsl:when test="text()='1111'">
               <xsl:text>char</xsl:text> <xsl:apply-templates select="../dimensione"/>
           </xsl:when>

           <!--xsl:when test="text()='1111'">
               <xsl:text>int2vector</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>regproc</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>tid</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>xid</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>cid</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>oidvector</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>SET</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_type</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_attribute</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_proc</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_class</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_shadow</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_group</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_database</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>smgr</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>point</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>lseg</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>path</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>box</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>polygon</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>line</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>_line</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>abstime</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>reltime</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>tinterval</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>unknown</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>circle</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>_circle</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>macaddr</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>inet</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>cidr</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>_char</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>_int2vector</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>_regproc</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>_tid</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>_xid</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>_cid</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>_oidvector</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>_point</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>_lseg</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>_path</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>_box</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>_abstime</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>_reltime</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>_tinterval</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>_polygon</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>aclitem</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>_aclitem</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>_macaddr</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>_inet</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>_cidr</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>interval</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>_interval</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>varbit</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>_varbit</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>refcursor</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>_refcursor</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>regprocedure</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>regoper</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>regoperator</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>regclass</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>regtype</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>_regprocedure</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>_regoper</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>_regoperator</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>_regclass</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>_regtype</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>record</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>cstring</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>any</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>anyarray</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>void</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>trigger</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>language_handler</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>internal</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>opaque</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>anyelement</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_attrdef</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_constraint</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_inherits</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_index</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_operator</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_opclass</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_am</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_amop</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_amproc</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_language</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_largeobject</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_aggregate</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_statistic</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_rewrite</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_trigger</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_listener</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_description</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_cast</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_namespace</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_conversion</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_depend</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_toast_16384</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_toast_16386</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_toast_1262</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_toast_16416</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_toast_1261</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_toast_1255</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_toast_16410</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_toast_1260</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_toast_16408</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_user</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_rules</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_views</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_tables</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_indexes</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_stats</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_stat_all_tables</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_stat_sys_tables</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_stat_user_tables</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_statio_all_tables</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_statio_sys_tables</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_statio_user_tables</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_stat_all_indexes</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_stat_sys_indexes</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_stat_user_indexes</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_statio_all_indexes</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_statio_sys_indexes</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_statio_user_indexes</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_statio_all_sequences</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_statio_sys_sequences</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_statio_user_sequences</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_stat_activity</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_stat_database</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_locks</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_settings</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_toast_17070</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>sql_implementation_info</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>cardinal_number</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>character_data</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>sql_identifier</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>information_schema_catalog_name</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>time_stamp</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>applicable_roles</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>check_constraints</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>column_domain_usage</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>column_privileges</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>column_udt_usage</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>columns</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>constraint_column_usage</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>constraint_table_usage</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>domain_constraints</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>domain_udt_usage</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>domains</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>enabled_roles</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>key_column_usage</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>parameters</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>referential_constraints</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>role_column_grants</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>role_routine_grants</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>role_table_grants</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>role_usage_grants</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>routine_privileges</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>routines</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>schemata</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>sql_features</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_toast_17075</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>sql_languages</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_toast_17080</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>sql_packages</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_toast_17085</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>sql_sizing</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_toast_17090</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>sql_sizing_profiles</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_toast_17095</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>table_constraints</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>table_privileges</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>tables</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>triggered_update_columns</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>triggers</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>usage_privileges</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>view_column_usage</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>view_table_usage</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>views</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>data_type_privileges</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>element_types</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>qrtz_job_details</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_toast_17150</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>qrtz_job_listeners</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>qrtz_triggers</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>qrtz_simple_triggers</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>qrtz_cron_triggers</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>qrtz_blob_triggers</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_toast_17189</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>qrtz_trigger_listeners</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>qrtz_calendars</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>pg_toast_17208</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>qrtz_paused_trigger_grps</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>qrtz_fired_triggers</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>qrtz_scheduler_state</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='1111'">
               <xsl:text>qrtz_locks</xsl:text>
           </xsl:when-->

		   <!-- ARRAY -->
           <!--xsl:when test="text()='2003'">
               <xsl:text>_money</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='2003'">
               <xsl:text>_bool</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='2003'">
               <xsl:text>_bytea</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='2003'">
               <xsl:text>_name</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='2003'">
               <xsl:text>_int2</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='2003'">
               <xsl:text>_int4</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='2003'">
               <xsl:text>_text</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='2003'">
               <xsl:text>_oid</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='2003'">
               <xsl:text>_bpchar</xsl:text>
           </xsl:when-->

           <xsl:when test="text()='2003'">
               <xsl:text>_varchar</xsl:text> <xsl:apply-templates select="../dimensione"/>
           </xsl:when>

           <!--xsl:when test="text()='2003'">
               <xsl:text>_int8</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='2003'">
               <xsl:text>_float4</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='2003'">
               <xsl:text>_float8</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='2003'">
               <xsl:text>_timestamp</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='2003'">
               <xsl:text>_date</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='2003'">
               <xsl:text>_time</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='2003'">
               <xsl:text>_timestamptz</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='2003'">
               <xsl:text>_numeric</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='2003'">
               <xsl:text>_timetz</xsl:text>
           </xsl:when-->

           <!--xsl:when test="text()='2003'">
               <xsl:text>_bit</xsl:text>
           </xsl:when-->

			<xsl:otherwise>
				<xsl:text>### TIPO </xsl:text> <xsl:value-of select="text()"/> <xsl:text> NON SUPPORTATO ###</xsl:text>
			</xsl:otherwise>

		</xsl:choose>
		
	</xsl:template>

	<xsl:template match="defaultValue">
		<xsl:text> DEFAULT '</xsl:text> <xsl:value-of select="text()"/> <xsl:text>'</xsl:text>
	</xsl:template>

	<xsl:template match="ammettiNull">

		<xsl:choose>
			<xsl:when test="text()='false'">
				<xsl:text> NOT NULL</xsl:text>
			</xsl:when>

			<xsl:otherwise>
				<xsl:text> NULL</xsl:text>
			</xsl:otherwise>
		</xsl:choose>
		
	</xsl:template>

</xsl:stylesheet>