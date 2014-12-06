<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="text" indent="yes"/>



	<xsl:template match="tabelle">
		<xsl:apply-templates/>
	</xsl:template>




	<xsl:template match="tabella">
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
		<xsl:apply-templates select="primaryKey"/>
		<xsl:apply-templates select="defaultValue"/>


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
				<xsl:text>BIT</xsl:text>
				<xsl:apply-templates select="../dimensione"/>
			</xsl:when>

			<xsl:when test="text()='-6'">
				<xsl:text>TINYINT</xsl:text>
				<xsl:apply-templates select="../dimensione"/>
			</xsl:when>

			<xsl:when test="text()='-5'">
				<xsl:text>BIGINT</xsl:text>
				<xsl:apply-templates select="../dimensione"/>
			</xsl:when>

			<xsl:when test="text()='-4'">
				<xsl:text>IMAGE</xsl:text>
				<xsl:apply-templates select="../dimensione"/>
			</xsl:when>

			<xsl:when test="text()='-3'">
				<xsl:text>VARBINARY</xsl:text>
				<xsl:apply-templates select="../dimensione"/>
			</xsl:when>

			<xsl:when test="text()='-2'">
				<xsl:text>BINARY</xsl:text>
				<xsl:apply-templates select="../dimensione"/>
			</xsl:when>

			<xsl:when test="text()='-1'">
				<xsl:text>TEXT</xsl:text>
				<xsl:apply-templates select="../dimensione"/>
			</xsl:when>

			<xsl:when test="text()='1'">
				<xsl:text>CHAR</xsl:text>
				<xsl:apply-templates select="../dimensione"/>
			</xsl:when>

			<xsl:when test="text()='2'">
				<xsl:text>NUMERIC</xsl:text>
				<xsl:apply-templates select="../dimensione"/>
			</xsl:when>

			<xsl:when test="text()='3'">
				<xsl:text>DECIMAL</xsl:text>
				<xsl:apply-templates select="../dimensione"/>
			</xsl:when>

			<xsl:when test="text()='4'">
				<xsl:text>INT</xsl:text>
			</xsl:when>

			<xsl:when test="text()='5'">
				<xsl:text>SMALLINT</xsl:text>
				<xsl:apply-templates select="../dimensione"/>
			</xsl:when>

			<xsl:when test="text()='6'">
				<xsl:text>FLOAT</xsl:text>
				<xsl:apply-templates select="../dimensione"/>
			</xsl:when>

			<xsl:when test="text()='7'">
				<xsl:text>REAL</xsl:text>
				<xsl:apply-templates select="../dimensione"/>
			</xsl:when>

			<!-- Double -->
			<xsl:when test="text()='8'">
				<xsl:text>FLOAT</xsl:text>
				<xsl:apply-templates select="../dimensione"/>
			</xsl:when>

			<xsl:when test="text()='12'">
				<xsl:text>VARCHAR</xsl:text>
				<xsl:apply-templates select="../dimensione"/>
			</xsl:when>

			<!-- Date -->
			<xsl:when test="text()='91'">
				<xsl:text>DATETIME</xsl:text>
			</xsl:when>

			<!-- Time -->
			<xsl:when test="text()='92'">
				<xsl:text>DATETIME</xsl:text>
			</xsl:when>

			<xsl:when test="text()='93'">
				<xsl:text>DATETIME</xsl:text>
			</xsl:when>

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