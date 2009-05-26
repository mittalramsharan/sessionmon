<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">

<xsl:for-each select="replicationTest/error">
<font color="red"><xsl:value-of select="."/></font><br/>
</xsl:for-each>

<table id="table-highlight" summary="Session Replication Test">
<thead>
<tr>
<th scope="col">Node</th>
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
	<xsl:for-each select="replicationTest/session">
	<tr>
	<td><xsl:value-of select="serverName"/>:<xsl:value-of select="serverPort"/></td>
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
		<th colspan="4">Unmatching Attributes</th>
		</tr>
		</thead>
		<tbody>
			<xsl:for-each select="replicationTest/session/replicationFailedAttributes">
			<xsl:sort select="name"/>
			<tr>
			<td width="15%"><xsl:value-of select="server"/></td>
			<td width="15%"><xsl:value-of select="name"/><br/>(<xsl:value-of select="objectType"/>)</td>
			<td width="55%"><xsl:value-of select="toStringValue"/></td>
			<td width="15%"><xsl:value-of select="objectGraphSizeInBytes"/> bytes (Object Graph)<br/><xsl:value-of select="objectSerializedSizeInBytes"/> bytes (Serialized)</td>
			</tr>
			</xsl:for-each>
		</tbody>
</table>

</xsl:template>
</xsl:stylesheet>
