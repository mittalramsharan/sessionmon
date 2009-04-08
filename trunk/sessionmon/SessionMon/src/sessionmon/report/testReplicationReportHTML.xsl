<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">

<table id="table-highlight" summary="Session Replication Test">
<thead>
<tr>
<th scope="col">Node</th>
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
	<xsl:for-each select="replicationTest/session">
	<tr>
	<td><xsl:value-of select="serverName"/></td>
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
			<xsl:for-each select="replicationTest/session/attributes">
			<tr>
			<td width="25%"><xsl:value-of select="name"/><br/>(<xsl:value-of select="objectType"/>)</td>
			<td width="50%"><xsl:value-of select="toStringValue"/></td>
			<td width="25%">Object Graph Size = <xsl:value-of select="objectGraphSizeInBytes"/> bytes<br/>Serialized Size = <xsl:value-of select="objectSerializedSizeInBytes"/> bytes</td>
			</tr>
			</xsl:for-each>
		</tbody>
</table>

</xsl:template>
</xsl:stylesheet>
