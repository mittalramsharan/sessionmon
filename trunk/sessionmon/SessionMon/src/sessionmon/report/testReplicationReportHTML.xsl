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
	<td><xsl:value-of select="serverName"/>:<xsl:value-of select="serverPort"/></td>
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
		<th colspan="4">Attributes with Replication Error or Delay</th>
		</tr>
		</thead>
		<tbody>
			<xsl:for-each select="replicationTest/session/replicationFailedAttributes">
			<xsl:sort select="name"/>
			<tr>
			<td width="15%"><xsl:value-of select="../serverName"/>:<xsl:value-of select="../serverPort"/></td>
			<td width="15%"><xsl:value-of select="name"/><br/>(<xsl:value-of select="objectType"/>)</td>
			<td width="55%"><xsl:value-of select="toStringValue"/></td>
			<td width="15%"><xsl:value-of select="objectGraphSizeInBytes"/> bytes (Object Graph)<br/><xsl:value-of select="objectSerializedSizeInBytes"/> bytes (Serialized)</td>
			</tr>
			</xsl:for-each>
		</tbody>
</table>

</xsl:template>
</xsl:stylesheet>
