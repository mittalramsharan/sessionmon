<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
version="1.0">
<xsl:template match="/">

<table summary="Session Information">
<thead>
<tr>
<th scope="col">Total Number of Attributes</th>
<th scope="col">Total Object Graph Size</th>
<th scope="col">Total Serialized Size</th>
<th scope="col">Creation Time</th>
<th scope="col">Last Accessed Time</th>
<th scope="col">Max Inactive Interval</th>
<th scope="col">Is New</th>
</tr>
</thead>
<tbody>
	<xsl:for-each select="session">
	<tr>
	<td><xsl:value-of select="totalNumberOfAttributes"/></td>
	<td><xsl:value-of select="totalObjectGraphSizeInBytes"/> bytes</td>
	<td><xsl:value-of select="totalObjectSerializedSizeInBytes"/> bytes</td>
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
			<tr>
			<td><xsl:value-of select="name"/><br/>(<xsl:value-of select="objectType"/>)</td>
			<td><xsl:value-of select="toStringValue"/></td>
			<td width="200px">Object Graph Size = <xsl:value-of select="objectGraphSizeInBytes"/> bytes<br/>Serialized Size = <xsl:value-of select="objectSerializedSizeInBytes"/> bytes</td>
			</tr>
			</xsl:for-each>
		</tbody>
</table>

</xsl:template>
</xsl:stylesheet>
