10:34 AM 8/7/2014<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:template match="/">
		<HTML>
			<HEAD>
				<LINK REL="StyleSheet" HREF="C://Program Files (x86)//Reporting//Report.css" TYPE="text/css"/>
				<SCRIPT language="javascript" type="text/javascript">
					//Toggle Vertical Menu
					function toggleMenu(id,x) {
					if (document.getElementById) {
					var e = document.getElementById(id);
					var b = document.getElementById(x);
					if (e) {
					if (e.style.display != "block") {
					e.style.display = "block";
					b.src="C:\\Program Files (x86)\\Reporting\\Minus.gif";
					}
					else {
					e.style.display = "none";
					b.src="C:\\Program Files (x86)\\Reporting\\Plus.gif";
					}
					}
					}
					}

					function expandall() {
					var e = document.all.tags("DIV");
					var b = document.all.tags("img");

					for (var i = 0; i &lt; e.length; i++) {
					if (e[i].style.display == "none") {
					e[i].style.display = "block";
					}
					}
					for (var i = 0; i &lt; b.length; i++) {
					if (b[i].id != "m1" &amp;&amp; b[i].id != "m2") {
					if (b[i].src.substring(b[i].src.length-14, b[i].src.length) == "check-plus.jpg") {
					b[i].src = "C:\\Program Files (x86)\\Reporting\\Minus.gif";
					}
					}
					}
					}

					//the function takes in as input the div position and expands all its descendant nodes
					function expandtofailure(id) {
					var parent = document.getElementById(id);
					var all = parent.getElementsByTagName('div');
					var icon = parent.getElementsByTagName("img");

					for (var i = 0; i &lt; all.length; i++) {
					all[i].style.display = "block";
					}
					for (var i = 0; i &lt; all.length; i++) {
					icon[i].src="C:\\Program Files (x86)\\Reporting\\Minus.gif";
					}
					}

					function collapseall() {
					var e = document.all.tags("DIV");
					var b = document.all.tags("img");

					for (var i = 0; i &lt; e.length; i++) {
					if(e[i].id != "TestCase" &amp;&amp; e[i].id != "TestFlow" &amp;&amp; e[i].id != "TestActivity" &amp;&amp; e[i].id != "TestMethod" &amp;&amp; e[i].id != "TestSubMethod" &amp;&amp; e[i].id != "MethodResult" &amp;&amp; e[i].id != "SubMethodResult") {
					if (e[i].style.display == "block") {
					e[i].style.display = "none";
					}
					}
					}
					for (var i = 0; i &lt; b.length; i++) {
					if (b[i].id != "m1" &amp;&amp; b[i].id != "m2") {
					if (b[i].src.substring(b[i].src.length-13, b[i].src.length) == "check-min.jpg") {
					b[i].src = "C:\\Program Files (x86)\\Reporting\\Plus.gif";
					}
					}
					}
					}
				</SCRIPT>
			</HEAD>
			<BODY>
				<table bgcolor="#FFFFFF" bordercolorlight="#C0C0C0" bordercolordark="#C0C0C0" border="1" width="100%" height="45">
					<tr height="21">
						<td valign="center" align="center" width="20%">
							<img src="C:\\Program Files (x86)\\Reporting\\Logo.gif"></img>

						</td>
						<td valign="center" align="center" width="80%">
							<p align="center">
								<font color="#00002C" face="Verdana" style="font-size: 25pt">
									ITJumpStart Automation Test Execution Report
								</font>
							</p>
						</td>
					</tr>
				</table>
				<BR></BR>
				<TABLE cellSpacing="1" cellPadding="1" align="center" Class="TABLEHEAD">
					<TR>
						<TD class="message">Overall Results</TD>
						<TD class="passed">
							Passed -
							<xsl:value-of select ="count(Report/TestCase[@TCStatus='1']) + count(Report/TestCase[@TCStatus='11'])"></xsl:value-of>
						</TD>
						<TD class="failed">
							Failed -
							<xsl:value-of select ="count(Report/TestCase[@TCStatus='0']) + count(Report/TestCase[@TCStatus='10'])"></xsl:value-of>
						</TD>
						<TD class="skipped">
							Skipped -
							<xsl:value-of select ="count(Report/TestCase[@TCStatus='7'])"></xsl:value-of>
						</TD>
					</TR>
				</TABLE>
				<BR></BR>
				<font face="Arial" style="font-size: 10pt">
					<TABLE cellSpacing="1" cellPadding="1" align="center" Class="PrevNext">
						<TR>
							<TD width="10%">
								<img src="C:\\Program Files (x86)\\Reporting\\Plus.gif" onClick="expandall()" id="m1"></img>
								<xsl:text> </xsl:text>
								<a href="#" onClick="expandall()">Expand All</a>
								<xsl:text> </xsl:text>
							</TD>
							<TD width="10%">
								<img src="C:\\Program Files (x86)\\Reporting\\Minus.gif" onClick="collapseall()" id="m2"></img>
								<xsl:text> </xsl:text>
								<a href="#" onClick="collapseall()">Collapse All</a>
								<xsl:text> </xsl:text>
							</TD>
							<td width="75%">
								<center>
									<b>
										<xsl:choose>
											<xsl:when test="Report/@XMLNum='1'">
												<xsl:text>Prev</xsl:text>
											</xsl:when>
											<xsl:otherwise>
												<A target="_top">
													<xsl:attribute name="href">
														<xsl:value-of select="Report/@PrevXMLFileName" /><xsl:value-of select="number(number(Report/@XMLNum)-1)"/>.xml
													</xsl:attribute>Prev
												</A>
											</xsl:otherwise>
										</xsl:choose>
										<xsl:text> &lt;===== Page </xsl:text>
										<xsl:value-of select="number(Report/@XMLNum)" />
										<xsl:text> of </xsl:text>
										<xsl:value-of select="number(Report/@LastXMLNum)" />
										<xsl:text>  =====&gt; </xsl:text>
										<xsl:choose>
											<xsl:when test="Report/@LastXMLNum &gt; 1 and Report/@XMLNum != Report/@LastXMLNum">
												<A target="_top">
													<xsl:attribute name="href">
														<xsl:value-of select="Report/@NextXMLFileName" /><xsl:value-of select="number(number(Report/@XMLNum)+1)"/>.xml
													</xsl:attribute>Next
												</A>
											</xsl:when>
											<xsl:otherwise>
												<xsl:text>Next</xsl:text>
											</xsl:otherwise>
										</xsl:choose>
									</b>
								</center>
							</td>
						</TR>
					</TABLE>
				</font>
				<BR></BR>
				<TABLE bgcolor="#FFFFFF" bordercolorlight="#C0C0C0" bordercolordark="#C0C0C0" border="1" cellSpacing="1" cellPadding="1" align="center" Class="EnvHeader">
					<TR class="message">
						
						<TD width="20%">
							Environment: 
						           <font color="#5746A1" face="arial" style="font-size: 10 pt">
                                                           <xsl:value-of select="Report/@Env"></xsl:value-of>
						           </font>
						</TD>
						<TD width="30%">
							
							Execution Date: 
							<font color="#5746A1" face="arial" style="font-size: 10 pt">
                                                           <xsl:value-of select="Report/@DateTime"></xsl:value-of>
							</font>
						</TD>
					</TR>
				</TABLE>
				
				<TABLE bgcolor="#FFFFFF" bordercolorlight="#C0C0C0" bordercolordark="#C0C0C0" border="1" cellSpacing="1" cellPadding="1" align="center" Class="EnvHeader">
					<TR class="message">
						<TD width="50%">
							
							   Suit Name: 
              						<font color="#5746A1" face="arial" style="font-size: 10 pt">
                                                           <xsl:value-of select="Report/@RunFlow"></xsl:value-of>
							</font>
						</TD>
						<TD width="50%">
							
							   Build Version: 
								<font color="#5746A1" face="arial" style="font-size: 10 pt">
									<xsl:value-of select="Report/@BuildVersion"></xsl:value-of>
								</font>
						</TD>
					</TR>
				</TABLE>
				<TABLE bgcolor="#FFFFFF" bordercolorlight="#C0C0C0" bordercolordark="#C0C0C0" border="1" cellSpacing="1" cellPadding="1" align="center" Class="EnvHeader">
					<TR class="message">
						<TD width="100%">
							Input Sheet Path: 
								<font color="#5746A1" face="arial" style="font-size: 10 pt">
									<xsl:value-of select="Report/@InputFilePath"></xsl:value-of>
								</font>
						</TD>
					</TR>
				</TABLE>
				<BR></BR>
				<xsl:apply-templates select="Report/TestCase"></xsl:apply-templates>
			</BODY>
		</HTML>
	</xsl:template>

	<xsl:template match="Report/TestCase">
		<DIV id="TestCase" Style="POSITION: relative; display:block" class="TABLEHEAD">
			<TABLE cellSpacing="1" cellPadding="1" align="Left" Class="TestCase" onClick="toggleMenu('DIV{position()}.0', 'm{position()}.0')">
				<TR align="Left">
					<TD class="message">
						<xsl:choose>
							<xsl:when test="@TCStatus='0'">
								<img src="C:\\Program Files (x86)\\Reporting\\False.gif"></img>

							</xsl:when>
							<xsl:when test="@TCStatus='10'">
								<img src="C:\\Program Files (x86)\\Reporting\\False.gif"></img>

							</xsl:when>
							<xsl:when test="@TCStatus='7'">
								<img src="\\\\Lab.esurance.com\\QA_Automation\\Auto\\!!!AutomationData\\resources\\supportfiles\\Skip.jpg"></img>

							</xsl:when>
							<xsl:otherwise>
								<img src="C:\\Program Files (x86)\\Reporting\\True.gif"></img>

							</xsl:otherwise>
						</xsl:choose>
						<xsl:text> </xsl:text>
						<img id="m{position()}.0" src="C:\\Program Files (x86)\\Reporting\\Plus.gif" border="0" ></img>
						<xsl:text> </xsl:text>
						<xsl:value-of select="@Desc"></xsl:value-of>
					</TD>

					<!--Nainappa's Changes start -->
					<TD align="Right" width="7%">
						<xsl:if test="@TCStatus mod 10 = 0">
							<a onClick="expandtofailure('DIV{position()}.0')" href="#fail_{@Row}">
								<img alt="Goto First Failure" src="C:\\Program Files (x86)\\Reporting\\FirstDown.gif" border="0" ></img>
							</a>
						</xsl:if>
					</TD>
					<!-- Nainappa's Changes end -->

					<TD class="passed" align="Right">
						Passed -
						<xsl:value-of select ="(count(TestFlow/TestActivity/TestMethod/MethodResult[@Status='1']) + count(TestFlow/TestActivity/TestMethod/MethodResult[@Status='21']) + count(TestFlow/TestActivity/TestMethod/TestSubMethod/SubMethodResult[@Status='1']) + count(TestFlow/TestActivity/TestMethod/TestSubMethod/SubMethodResult[@Status='21']))"></xsl:value-of>
						<xsl:if test="@TCStatus='11'">
							<xsl:text> *</xsl:text>
						</xsl:if>
					</TD>
					<TD class="failed" align="Right">
						Failed -
						<xsl:value-of select ="(count(TestFlow/TestActivity/TestMethod/MethodResult[@Status='0']) + count(TestFlow/TestActivity/TestMethod/MethodResult[@Status='20']) + count(TestFlow/TestActivity/TestMethod/TestSubMethod/SubMethodResult[@Status='0']) + count(TestFlow/TestActivity/TestMethod/TestSubMethod/SubMethodResult[@Status='20']))"></xsl:value-of>
						<xsl:if test="@TCStatus='10'">
							<xsl:text> *</xsl:text>
						</xsl:if>
					</TD>
					<TD class="warnings" align="Right">
						Warnings -
						<xsl:value-of select ="(count(TestFlow/TestActivity/TestMethod/MethodResult[@Status='2']) + count(TestFlow/TestActivity/TestMethod/TestSubMethod/SubMethodResult[@Status='2']))"></xsl:value-of>
					</TD>
					<TD class="additional_information" align="Right">
						Information -
						<xsl:value-of select ="(count(TestFlow/TestActivity/TestMethod/MethodResult[@Status='4']) + count(TestFlow/TestActivity/TestMethod/TestSubMethod/SubMethodResult[@Status='4']))"></xsl:value-of>
					</TD>
				</TR>
			</TABLE>
		</DIV>
		<DIV id="DIV{position()}.0" Style="display:none">
			<xsl:apply-templates select="TestFlow">
				<xsl:with-param name="TestCasePosition" select="position()"/>
			</xsl:apply-templates>
		</DIV>
		<BR></BR>
	</xsl:template>

	<xsl:template match="TestFlow">
		<xsl:param name="TestCasePosition"/>
		<DIV id="TestFlow" Style="LEFT: 20px; POSITION: relative; display:block">
			<TABLE cellSpacing="1" cellPadding="1" align="center" Class="TestFlow" onClick="toggleMenu('DIV{concat($TestCasePosition,'.',position())}', 'm{concat($TestCasePosition,'.',position())}')">
				<TR>
					<TD class="message">
						<img id="m{concat($TestCasePosition,'.',position())}" src="C:\\Program Files (x86)\\Reporting\\Plus.gif" border="0" ></img>
						<xsl:text> </xsl:text>
						<xsl:value-of select="@Desc"></xsl:value-of>
					</TD>
					<TD class="passed">
						Passed -
						<xsl:value-of select ="(count(TestActivity/TestMethod/MethodResult[@Status='1']) + count(TestActivity/TestMethod/MethodResult[@Status='21']) + count(TestActivity/TestMethod/TestSubMethod/SubMethodResult[@Status='1']) + count(TestActivity/TestMethod/TestSubMethod/SubMethodResult[@Status='21']))"></xsl:value-of>
					</TD>
					<TD class="failed">
						Failed -
						<xsl:value-of select ="(count(TestActivity/TestMethod/MethodResult[@Status='0']) + count(TestActivity/TestMethod/MethodResult[@Status='20']) + count(TestActivity/TestMethod/TestSubMethod/SubMethodResult[@Status='0']) + count(TestActivity/TestMethod/TestSubMethod/SubMethodResult[@Status='20']))"></xsl:value-of>
					</TD>
					<TD class="warnings">
						Warnings -
						<xsl:value-of select ="(count(TestActivity/TestMethod/MethodResult[@Status='2']) + count(TestActivity/TestMethod/TestSubMethod/SubMethodResult[@Status='2']))"></xsl:value-of>
					</TD>
					<TD class="additional_information">
						Information -
						<xsl:value-of select ="(count(TestActivity/TestMethod/MethodResult[@Status='4']) + count(TestActivity/TestMethod/TestSubMethod/SubMethodResult[@Status='4']))"></xsl:value-of>
					</TD>
				</TR>
			</TABLE>
		</DIV>
		<DIV id="DIV{concat($TestCasePosition,'.',position())}" Style="display:none">
			<xsl:apply-templates select="TestActivity">
				<xsl:with-param name="TestFlowPosition" select="concat($TestCasePosition,'.',position())"/>
			</xsl:apply-templates>
		</DIV>
	</xsl:template>

	<xsl:template match="TestActivity">
		<xsl:param name="TestFlowPosition"/>
		<DIV id="TestActivity" Style="LEFT: 40px; POSITION: relative; display:block"  >
			<TABLE cellSpacing="1" cellPadding="1" align="center" Class="TestActivity" onClick="toggleMenu('DIV{concat($TestFlowPosition,'.',position())}', 'm{concat($TestFlowPosition,'.',position())}')">
				<TR>
					<TD class="message">
						<img id="m{concat($TestFlowPosition,'.',position())}" src="C:\\Program Files (x86)\\Reporting\\Plus.gif" border="0" ></img>
						<xsl:text> </xsl:text>
						<xsl:value-of select="@Desc"></xsl:value-of>
					</TD>
					<TD class="passed">
						Passed -
						<xsl:value-of select ="(count(TestMethod/MethodResult[@Status='1']) + count(TestMethod/MethodResult[@Status='21']) + count(TestMethod/TestSubMethod/SubMethodResult[@Status='1']) + count(TestMethod/TestSubMethod/SubMethodResult[@Status='21']))"></xsl:value-of>
					</TD>
					<TD class="failed">
						Failed -
						<xsl:value-of select ="(count(TestMethod/MethodResult[@Status='0']) + count(TestMethod/MethodResult[@Status='20']) + count(TestMethod/TestSubMethod/SubMethodResult[@Status='0']) + count(TestMethod/TestSubMethod/SubMethodResult[@Status='20']))"></xsl:value-of>
					</TD>
					<TD class="warnings">
						Warnings -
						<xsl:value-of select ="(count(TestMethod/MethodResult[@Status='2']) + count(TestMethod/TestSubMethod/SubMethodResult[@Status='2']))"></xsl:value-of>
					</TD>
					<TD class="additional_information">
						Information -
						<xsl:value-of select ="(count(TestMethod/MethodResult[@Status='4']) + count(TestMethod/TestSubMethod/SubMethodResult[@Status='4']))"></xsl:value-of>
					</TD>
				</TR>
			</TABLE>
		</DIV>
		<DIV ID="DIV{concat($TestFlowPosition,'.',position())}" Style="display:none">
			<xsl:apply-templates select="TestMethod">
				<xsl:with-param name="TestActivityPosition" select="concat($TestFlowPosition,'.',position())"/>
			</xsl:apply-templates>
		</DIV>
	</xsl:template>

	<xsl:template match="TestMethod">
		<xsl:param name="TestActivityPosition"/>
		<DIV id="TestMethod" Style="LEFT: 60px; POSITION: relative; display:block"  >
			<TABLE cellSpacing="1" cellPadding="1" align="center" Class="TestMethod" onClick="toggleMenu('DIV{concat($TestActivityPosition,'.',position())}', 'm{concat($TestActivityPosition,'.',position())}')">
				<TR>
					<TD class="message">
						<img id="m{concat($TestActivityPosition,'.',position())}" src="C:\\Program Files (x86)\\Reporting\\Plus.gif" border="0" ></img>
						<xsl:text> </xsl:text>
						<xsl:value-of select="@Desc"></xsl:value-of>
					</TD>
					<TD class="passed">
						Passed -
						<xsl:value-of select ="(count(MethodResult[@Status='1']) + count(MethodResult[@Status='21']) + count(TestSubMethod/SubMethodResult[@Status='1']) + count(TestSubMethod/SubMethodResult[@Status='21']))"></xsl:value-of>
						<xsl:if test="MethodResult/@Status='21'">
							<xsl:text> *</xsl:text>
						</xsl:if>
					</TD>
					<TD class="failed">
						Failed -
						<xsl:value-of select ="(count(MethodResult[@Status='0']) + count(MethodResult[@Status='20']) + count(TestSubMethod/SubMethodResult[@Status='0']) + count(TestSubMethod/SubMethodResult[@Status='20']))"></xsl:value-of>
						<xsl:if test="MethodResult/@Status='20'">
							<xsl:text> *</xsl:text>
						</xsl:if>
					</TD>
					<TD class="warnings">
						Warnings -
						<xsl:value-of select ="(count(MethodResult[@Status='2']) + count(TestSubMethod/SubMethodResult[@Status='2']))"></xsl:value-of>
					</TD>
					<TD class="additional_information">
						Information -
						<xsl:value-of select ="(count(MethodResult[@Status='4']) + count(TestSubMethod/SubMethodResult[@Status='4']))"></xsl:value-of>
					</TD>
				</TR>
			</TABLE>
		</DIV>
		<Div ID="DIV{concat($TestActivityPosition,'.',position())}" Style="display:none">
			<xsl:variable name="MethodResult" select='"MethodResult"' />
			<xsl:variable name="TestSubMethod" select='"TestSubMethod"' />
			<xsl:for-each select="*">
				<xsl:choose>
					<xsl:when test="local-name()=$MethodResult">
						<xsl:call-template name="MethodResult" />
					</xsl:when>
					<xsl:when test="local-name()=$TestSubMethod">
						<xsl:call-template name="TestSubMethod">
							<xsl:with-param name="TestMethodPosition" select="concat($TestActivityPosition,'.',position())"/>
						</xsl:call-template>
					</xsl:when>
					<xsl:otherwise></xsl:otherwise>
				</xsl:choose>
			</xsl:for-each>
		</Div>
	</xsl:template>

	<xsl:template match="MethodResult" name="MethodResult">
		<DIV id="MethodResult" Style="LEFT: 80px;POSITION: relative; display:block">
			<TABLE cellSpacing="1" cellPadding="1" align="center" Class="MESSAGES">
				<xsl:element name="TR">
					<xsl:if test="position()mod 2">
						<xsl:attribute name="class">M1</xsl:attribute>
					</xsl:if>
					<xsl:if test="not(position()mod 2)">
						<xsl:attribute name="class">M2</xsl:attribute>
					</xsl:if>

					<xsl:element name="TD">
						<!--Nainappa -> To get ID from xml and set as ID for the html tag <td> - start-->
						<xsl:attribute name="id">
							<xsl:if test="@ID">
								<xsl:value-of select="@ID" />
							</xsl:if>
						</xsl:attribute>
						<!--Nainappa -> To get ID from xml and set as ID for the html tag <td> - end-->

						<xsl:attribute name="class">
							<xsl:if test="@Status='0' or @Status='20'">FAILED</xsl:if>
							<xsl:if test="@Status='3'">INFORMATION</xsl:if>
							<xsl:if test="@Status='4'">ADDITIONAL_INFORMATION</xsl:if>
							<xsl:if test="@Status='2'">WARNINGS</xsl:if>
							<xsl:if test="@Status='1' or @Status='21'">PASSED</xsl:if>
						</xsl:attribute>

						<xsl:text> - </xsl:text>
						<xsl:value-of select="."></xsl:value-of>
						<xsl:if test="@ImagePath">
							<xsl:text>  - </xsl:text>
							<a href="{@ImagePath}" target="_new">ScreenShot</a>
						</xsl:if>


						<!--Nainappa -> WebPageLink - start-->
						<xsl:if test="@WebPagePath">
							<xsl:text>  - </xsl:text>
							<a href="{@WebPagePath}" style="color:white;text-decoration:none;background-color:black" target="_new">WebPage</a>
						</xsl:if>
						<!--Nainappa-> WebPageLink - end-->
						
						<!--Nainappa -> WebPageLink - start-->
						<xsl:if test="@VideoRecording">
							<xsl:text>  - </xsl:text>
							<a href="{@VideoRecording}" style="color:white;text-decoration:none;background-color:black" target="_new">Video Recording</a>
						</xsl:if>
						<!--Nainappa-> WebPageLink - end-->

						<!--Nainappa-> getting back to top - start-->
						<xsl:if test="@ID">
							<div style="text-align: right;">
								<a href="#" style="color:black;text-decoration:none" onClick="collapseall()" >^ Back to Top</a>
							</div>
						</xsl:if>
						<!--Nainappa-> getting back to top - end-->


					</xsl:element>
				</xsl:element>

				

			</TABLE>
		</DIV>

	</xsl:template>

	<xsl:template match="TestSubMethod" name="TestSubMethod">
		<xsl:param name="TestMethodPosition"/>
		<DIV id="TestSubMethod" Style="LEFT: 80px; POSITION: relative; display:block"  >
			<TABLE cellSpacing="1" cellPadding="1" align="center" Class="TestSubMethod" onClick="toggleMenu('DIV{concat($TestMethodPosition,'.',position())}', 'm{concat($TestMethodPosition,'.',position())}')">
				<TR>
					<TD class="message">
						<img id="m{concat($TestMethodPosition,'.',position())}" src="\\\\Lab.esurance.com\\QA_Automation\\Auto\\!!!AutomationData\\resources\\supportfiles\\check-plus.jpg" border="0" ></img>
						<xsl:text> </xsl:text>
						<xsl:value-of select="@Desc"></xsl:value-of>
					</TD>
					<TD class="passed">
						Passed -
						<xsl:value-of select ="(count(SubMethodResult[@Status='1']) + count(SubMethodResult[@Status='21']))"></xsl:value-of>
						<xsl:if test="SubMethodResult/@Status='21'">
							<xsl:text> *</xsl:text>
						</xsl:if>
					</TD>
					<TD class="failed">
						Failed -
						<xsl:value-of select ="(count(SubMethodResult[@Status='0']) + count(SubMethodResult[@Status='20']))"></xsl:value-of>
						<xsl:if test="SubMethodResult/@Status='20'">
							<xsl:text> *</xsl:text>
						</xsl:if>
					</TD>
					<TD class="warnings">
						Warnings -
						<xsl:value-of select ="count(SubMethodResult[@Status='2'])"></xsl:value-of>
					</TD>
					<TD class="additional_information">
						Information -
						<xsl:value-of select ="count(SubMethodResult[@Status='4'])"></xsl:value-of>
					</TD>
				</TR>
			</TABLE>
		</DIV>
		<DIV ID="DIV{concat($TestMethodPosition,'.',position())}" Style="display:none">
			<xsl:apply-templates select="SubMethodResult">
			</xsl:apply-templates>
		</DIV>
	</xsl:template>

	<xsl:template match="SubMethodResult">
		<DIV id="MethodResult" Style="LEFT: 100px;POSITION: relative; display:block">
			<TABLE cellSpacing="1" cellPadding="1" align="center" Class="MESSAGES">
				<xsl:element name="TR">
					<xsl:if test="position()mod 2">
						<xsl:attribute name="class">M1</xsl:attribute>
					</xsl:if>
					<xsl:if test="not(position()mod 2)">
						<xsl:attribute name="class">M2</xsl:attribute>
					</xsl:if>
					<xsl:element name="TD">
						<!--Saran -> To get ID from xml and set as ID for the html tag <td> - start-->
						<xsl:attribute name="id">
							<xsl:if test="@ID">
								<xsl:value-of select="@ID" />
							</xsl:if>
						</xsl:attribute>
						<!--Saran -> To get ID from xml and set as ID for the html tag <td> - end-->
						<xsl:attribute name="class">
							<xsl:if test="@Status='0' or @Status='20'">FAILED</xsl:if>
							<xsl:if test="@Status='3'">INFORMATION</xsl:if>
							<xsl:if test="@Status='4'">ADDITIONAL_INFORMATION</xsl:if>
							<xsl:if test="@Status='2'">WARNINGS</xsl:if>
							<xsl:if test="@Status='1' or @Status='21'">PASSED</xsl:if>
						</xsl:attribute>
						<xsl:text> - </xsl:text>
						<xsl:value-of select="."></xsl:value-of>
						<xsl:if test="@ImagePath">
							<xsl:text>  - </xsl:text>
							<a href="{@ImagePath}" target="_new">ScreenShot</a>
						</xsl:if>

						<!--Saran -> WebPageLink - start-->
						<xsl:if test="@WebPagePath">
							<xsl:text>  - </xsl:text>
							<a href="{@WebPagePath}" style="color:white;text-decoration:none;background-color:black" target="_new">WebPage</a>
						</xsl:if>
						<!--Saran -> WebPageLink - end-->
						
						
						<!--Saran -> getting back to top - start-->
						<xsl:if test="@ID">
							<div style="text-align: right;">
								<a href="#" style="color:black;text-decoration:none" onClick="collapseall()" >^ Back to Top</a>
							</div>
						</xsl:if>
						<!--Saran -> getting back to top - end-->

					</xsl:element>
				</xsl:element>

			</TABLE>

			
		</DIV>

		
	</xsl:template>
</xsl:stylesheet>