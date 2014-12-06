<?xml version="1.0" encoding="ISO-8859-1"?> 
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="text" indent="yes"/>

	<xsl:template match="tabelle">
<xsl:apply-templates/>
	</xsl:template>




	<xsl:template match="tabella">
#----------------------------
# Table structure for <xsl:value-of select="@nome"/>
#----------------------------
CREATE TABLE <xsl:value-of select="@nome"/>(
	<xsl:apply-templates />
)
;

	</xsl:template>




	<xsl:template match="colonne">
		<xsl:apply-templates />
	</xsl:template>




	<xsl:template match="colonna">
		<xsl:value-of select="@nome"/><xsl:text> </xsl:text>
		<xsl:apply-templates select="tipo"/><xsl:text> </xsl:text>
		<xsl:apply-templates select="ammettiNull"/><xsl:text> </xsl:text>
		<xsl:text> </xsl:text>
        <xsl:apply-templates select="primaryKey"/><xsl:text> </xsl:text>
        
		<!-- Finchè non raggiungo l'ultima colonna della tabella metto una virgola e vado accapo-->
		<xsl:if test="position()!=last()"><xsl:text>,
	</xsl:text>
		</xsl:if>
	</xsl:template>




	<xsl:template match="primaryKey">
		<xsl:choose>
		  <xsl:when test="text()='true'">
			<xsl:text>PRIMARY KEY</xsl:text>
		  </xsl:when>
		</xsl:choose>
	</xsl:template>
	
	
	

	<xsl:template match="dimensione">
		<xsl:text>(</xsl:text><xsl:value-of select="text()"/><xsl:text>) </xsl:text>	
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
		  <xsl:when test="text()='-5'">
			<xsl:text>BIGINT</xsl:text>
			<xsl:apply-templates select="../defaultValue"><xsl:with-param name="isLiteral" select="'false'"/></xsl:apply-templates>
		  </xsl:when>
		  <xsl:when test="text()='-4'">
			<xsl:text>LONG VARCHAR FOR BIT DATA</xsl:text> <xsl:apply-templates select="../dimensione"/>
			<xsl:apply-templates select="../defaultValue"><xsl:with-param name="isLiteral" select="'true'"/></xsl:apply-templates>
		  </xsl:when>
		  <xsl:when test="text()='-3'">
			<xsl:text>VARCHAR () FOR BIT DATA</xsl:text> <xsl:apply-templates select="../dimensione"/>
			<xsl:apply-templates select="../defaultValue"><xsl:with-param name="isLiteral" select="'true'"/></xsl:apply-templates>
		  </xsl:when>
		  <xsl:when test="text()='-2'">
			<xsl:text>CHAR () FOR BIT DATA</xsl:text> <xsl:apply-templates select="../dimensione"/>
			<xsl:apply-templates select="../defaultValue"><xsl:with-param name="isLiteral" select="'true'"/></xsl:apply-templates>
		  </xsl:when>
		  <xsl:when test="text()='-1'">
			<xsl:text>LONG VARCHAR</xsl:text> <xsl:apply-templates select="../dimensione"/>
			<xsl:apply-templates select="../defaultValue"><xsl:with-param name="isLiteral" select="'true'"/></xsl:apply-templates>
		  </xsl:when>
		  <xsl:when test="text()='1'">
			<xsl:text>CHAR</xsl:text> <xsl:apply-templates select="../dimensione"/>
			<xsl:apply-templates select="../defaultValue"><xsl:with-param name="isLiteral" select="'true'"/></xsl:apply-templates>
		  </xsl:when>
		  <xsl:when test="text()='2'">
			<xsl:text>NUMERIC</xsl:text>
			<xsl:apply-templates select="../defaultValue"><xsl:with-param name="isLiteral" select="'false'"/></xsl:apply-templates>
		  </xsl:when>
		  <xsl:when test="text()='3'">
			<xsl:text>DECIMAL</xsl:text> <xsl:apply-templates select="../dimensione"/>
			<xsl:apply-templates select="../defaultValue"><xsl:with-param name="isLiteral" select="'false'"/></xsl:apply-templates>
		  </xsl:when>
		  <xsl:when test="text()='4'">
			<xsl:text>INTEGER</xsl:text>
			<xsl:apply-templates select="../defaultValue"><xsl:with-param name="isLiteral" select="'false'"/></xsl:apply-templates>
		  </xsl:when>
		  <xsl:when test="text()='5'">
			<xsl:text>SMALLINT</xsl:text>
			<xsl:apply-templates select="../defaultValue"><xsl:with-param name="isLiteral" select="'false'"/></xsl:apply-templates>
		  </xsl:when>
		  <xsl:when test="text()='6'">
			<xsl:text>FLOAT</xsl:text>
			<xsl:apply-templates select="../defaultValue"><xsl:with-param name="isLiteral" select="'false'"/></xsl:apply-templates>
		  </xsl:when>
		  <xsl:when test="text()='7'">
			<xsl:text>REAL</xsl:text>
			<xsl:apply-templates select="../defaultValue"><xsl:with-param name="isLiteral" select="'false'"/></xsl:apply-templates>
		  </xsl:when>
		  <xsl:when test="text()='8'">
			<xsl:text>DOUBLE</xsl:text>
			<xsl:apply-templates select="../defaultValue"><xsl:with-param name="isLiteral" select="'false'"/></xsl:apply-templates>
		  </xsl:when>
		  <xsl:when test="text()='12'">
			<xsl:text>VARCHAR</xsl:text> <xsl:apply-templates select="../dimensione"/>
			<xsl:apply-templates select="../defaultValue"><xsl:with-param name="isLiteral" select="'true'"/></xsl:apply-templates>
		  </xsl:when>
	  	  <xsl:when test="text()='91'">
			<xsl:text>DATE</xsl:text>
			<xsl:apply-templates select="../defaultValue"><xsl:with-param name="isLiteral" select="'false'"/></xsl:apply-templates>
		  </xsl:when>
	  	  <xsl:when test="text()='92'">
			<xsl:text>TIME</xsl:text>
			<xsl:apply-templates select="../defaultValue"><xsl:with-param name="isLiteral" select="'false'"/></xsl:apply-templates>
		  </xsl:when>
	      <xsl:when test="text()='93'">
			<xsl:text>TIMESTAMP</xsl:text>
			<xsl:apply-templates select="../defaultValue"><xsl:with-param name="isLiteral" select="'false'"/></xsl:apply-templates>
		  </xsl:when>
		  <xsl:when test="text()='2004'">
			<xsl:text>BLOB</xsl:text> <xsl:apply-templates select="../dimensione"/>
			<xsl:apply-templates select="../defaultValue"><xsl:with-param name="isLiteral" select="'false'"/></xsl:apply-templates>
		  </xsl:when>
		  <xsl:when test="text()='2005'">
			<xsl:text>CLOB</xsl:text> <xsl:apply-templates select="../dimensione"/>
			<xsl:apply-templates select="../defaultValue"><xsl:with-param name="isLiteral" select="'true'"/></xsl:apply-templates>
		  </xsl:when>
		  <xsl:when test="text()='2006'">
			<xsl:text>XML</xsl:text> <xsl:apply-templates select="../dimensione"/>
			<xsl:apply-templates select="../defaultValue"><xsl:with-param name="isLiteral" select="'false'"/></xsl:apply-templates>
		  </xsl:when>
		  <xsl:otherwise/>
	</xsl:choose>
</xsl:template>




	<xsl:template match="ammettiNull">

		<xsl:choose>
			<xsl:when test="text()='false'">
				<xsl:text>NOT NULL</xsl:text>
			</xsl:when>

			<!--xsl:otherwise>
				<xsl:text>NULL</xsl:text>
			</xsl:otherwise-->
		</xsl:choose>
		
	</xsl:template>




	<xsl:template match="defaultValue">
		<xsl:param name="isLiteral" select="'false'"/>
		<xsl:choose>
			<xsl:when test="$isLiteral = 'true'">
				<xsl:text> DEFAULT '</xsl:text> <xsl:value-of select="text()"/> <xsl:text>'</xsl:text>
			</xsl:when>
			<xsl:otherwise>
				<xsl:text> DEFAULT </xsl:text> <xsl:value-of select="text()"/>
			</xsl:otherwise>
		</xsl:choose>
		
	</xsl:template>





</xsl:stylesheet>