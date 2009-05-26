<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
version="1.0">
<xsl:template match="/">

<table id="table-highlight" summary="Session Information">
<thead>
<tr>
<th scope="col"># of Other Active Sessions</th> 
<th scope="col"># of Session Attributes</th>
<th scope="col">Latest Session Attribute Update Time</th>
<th scope="col">Total Size of All Session Attributes</th>
<th scope="col">Creation Time</th>
<th scope="col">Last Accessed Time</th>
<th scope="col">Max Inactive Interval</th>
<th scope="col">Is New</th>
</tr>
</thead>
<tbody>
	<xsl:for-each select="session">
	<tr>
	<td><xsl:value-of select="totalNumberOfOtherActiveSessions"/></td>
	<td><xsl:value-of select="totalNumberOfAttributes"/></td>
	<td><xsl:value-of select="lastAttributeUpdateTime"/></td>
	<td><xsl:value-of select="totalObjectGraphSizeInBytes"/> bytes (Object Graph)<br/>
		<xsl:value-of select="totalObjectSerializedSizeInBytes"/> bytes (Serialized)</td>
	<td><xsl:value-of select="creationTime"/></td>
	<td><xsl:value-of select="lastAccessedTime"/></td>
	<td><xsl:value-of select="maxInactiveIntervalInSeconds"/> seconds</td>
	<td><xsl:value-of select="new"/></td>
	</tr>
	</xsl:for-each>
</tbody>
</table>
<br/>
<table id="table-highlight" summary="Session Attributes">
		<thead>
		<tr>
		<th colspan="3">Attributes</th>
		</tr>
		</thead>
		<tbody>
			<xsl:for-each select="session/attributes">
			<xsl:sort select="name"/>
			<tr>
			<td width="25%"><xsl:value-of select="name"/><br/>(<xsl:value-of select="objectType"/>)</td>
			<td width="50%"><xsl:value-of select="toStringValue"/></td>
			<td width="25%"><xsl:value-of select="objectGraphSizeInBytes"/> bytes (Object Graph)<br/><xsl:value-of select="objectSerializedSizeInBytes"/> bytes (Serialized)</td>
			</tr>
			</xsl:for-each>
		</tbody>
</table>

</xsl:template>
</xsl:stylesheet>
