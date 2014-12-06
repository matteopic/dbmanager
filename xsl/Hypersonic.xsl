<?xml version="1.0" encoding="ISO-8859-1"?> 
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="text" indent="yes"/>

	<xsl:template match="tabelle">
		<xsl:apply-templates/>
	</xsl:template>




	<xsl:template match="tabella">
		CREATE TABLE <xsl:value-of select="@nome"/>(
			<xsl:apply-templates/>
		);

	</xsl:template>




	<xsl:template match="colonne">
		<xsl:apply-templates/>
	</xsl:template>




	<xsl:template match="colonna">
		<xsl:value-of select="@nome"/><xsl:text> </xsl:text>
		<xsl:apply-templates select="tipo"/>
		<xsl:apply-templates select="dimensione"/>
		<xsl:apply-templates select="ammettiNull"/>
		<xsl:apply-templates select="primaryKey"/>
		<xsl:apply-templates select="defaultValue"/>


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
		<xsl:text> (</xsl:text><xsl:value-of select="text()"/><xsl:text>)</xsl:text>
	</xsl:template>


<xsl:template match="tipo">
	<!-- 
	Mapping tra i valori di java.sql.Types e i tipi di dato di Hypersonic
	-->
	<xsl:choose>
	  <xsl:when test="text()='-6'">
		<xsl:text>TINYINT</xsl:text>
	  </xsl:when>
	  
	  <xsl:when test="text()='-5'">
		<xsl:text>BIGINT</xsl:text>
	  </xsl:when>

	  <xsl:when test="text()='-4'">
		<xsl:text>LONGVARBINARY</xsl:text>
	  </xsl:when>

	  <xsl:when test="text()='-3'">
		<xsl:text>VARBINARY</xsl:text>
	  </xsl:when>

	  <xsl:when test="text()='-2'">
		<xsl:text>BINARY</xsl:text>
	  </xsl:when>

	  <xsl:when test="text()='-1'">
		<xsl:text>LONGVARCHAR</xsl:text>
	  </xsl:when>

	  <xsl:when test="text()='1'">
		<xsl:text>CHAR</xsl:text>
	  </xsl:when>

	  <xsl:when test="text()='2'">
		<xsl:text>NUMERIC</xsl:text>
	  </xsl:when>

	  <xsl:when test="text()='3'">
		<xsl:text>DECIMAL</xsl:text>
	  </xsl:when>

	  <xsl:when test="text()='4'">
		<xsl:text>INTEGER</xsl:text>
	  </xsl:when>

	  <xsl:when test="text()='5'">
		<xsl:text>SMALLINT</xsl:text>
	  </xsl:when>

	  <xsl:when test="text()='6'">
		<xsl:text>FLOAT</xsl:text>
	  </xsl:when>
	  
	  <xsl:when test="text()='7'">
		<xsl:text>REAL</xsl:text>
	  </xsl:when>	  
	  
	  <xsl:when test="text()='8'">
		<xsl:text>DOUBLE</xsl:text>
	  </xsl:when>
	  
	  <xsl:when test="text()='12'">
		<xsl:text>VARCHAR</xsl:text>
	  </xsl:when>
	  
	  <xsl:when test="text()='16'">
		<xsl:text>BOOLEAN</xsl:text>
	  </xsl:when>	  
	  
	  <xsl:when test="text()='91'">
		<xsl:text>DATE</xsl:text>
	  </xsl:when>	  
	  
	  <xsl:when test="text()='92'">
		<xsl:text>TIME</xsl:text>
	  </xsl:when>

	  <xsl:when test="text()='93'">
		<xsl:text>TIMESTAMP</xsl:text>
	  </xsl:when>
	  
	  <xsl:when test="text()='1111'">
		<xsl:text>OTHER</xsl:text>
	  </xsl:when>

		<xsl:otherwise>
			<xsl:text>### TIPO </xsl:text> <xsl:value-of select="text()"/> <xsl:text> NON SUPPORTATO ###</xsl:text>
		</xsl:otherwise>

	</xsl:choose>
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


	<xsl:template match="defaultValue">
		<xsl:text> DEFAULT '</xsl:text> <xsl:value-of select="text()"/> <xsl:text>'</xsl:text>
	</xsl:template>
</xsl:stylesheet>
