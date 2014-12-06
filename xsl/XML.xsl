<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
        
    <xsl:output method="text" />
        
    <xsl:template match="*">
          &lt;<xsl:value-of select="name()"/>&gt;
          	<xsl:value-of select="current()"/>
          &lt;/<xsl:value-of select="name()"/>&gt;


<!--xsl:text>&#xa;</xsl:text>
          Ancestor elements of this element
          <xsl:text>&#xa;</xsl:text>
          <xsl:text>&#xa;</xsl:text>
          <xsl:call-template name="printAncestors">
              <xsl:with-param name="node" select="." />          
          </xsl:call-template-->
    </xsl:template>     
    
    <!--xsl:template name="printAncestors">
       <xsl:param name="node" />  
       
       <xsl:choose>
                        <xsl:when test="$node/parent::*">
                <xsl:value-of select="name($node)" />,
                <xsl:call-template name="printAncestors">
                   <xsl:with-param name="node"
select="$node/ancestor::*"/>
                </xsl:call-template>
                        </xsl:when>
                        <xsl:otherwise>
               <xsl:value-of select="name($node)" />,                   
                        </xsl:otherwise>
           </xsl:choose>  
    </xsl:template-->
    
</xsl:stylesheet>