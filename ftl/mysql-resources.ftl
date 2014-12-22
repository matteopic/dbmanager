<html>
	<head>
		<style type="text/css">
			body{
				font-family: 'Verdana', sans-serrif;
			}
			.danger{ color: #d9534f; }
			.warning{ color: #f0ad4e; }			
			.primary{ color: #428bca; }
			.success{color: #5cb85c; }
		</style>
	</head>
	<body>
		<div>
			<p>
			Uptime ${Uptime?number?long / 3600}<br/>
			Avg. qps ${Questions?number?long / Uptime?number?long}<br/>
			Questions ${Questions}<br/>
			Threads Connected ${Threads_connected}
			</p>

<#if Uptime?number?long gt 172800>
  Server has been running for over 48hrs.<br/>
  It should be safe to follow these recommendations
<#else>
	<strong class="danger">
		Warning:<br/>
		Server has not been running for at least 48hrs.<br/>
		It may not be safe to use these recommendations
	</strong> 
</#if>

	<p class="danger">
	To find out more information on how each of these
	runtime variables effects performance visit:
	<#if major_version = "3.23" || major_version = "4.0" || major_version = "4.1">
		<a href="http://dev.mysql.com/doc/refman/4.1/en/server-system-variables.html">http://dev.mysql.com/doc/refman/4.1/en/server-system-variables.html</a>
	<#elseif major_version = "5.0" || mysql_version_num gt 050100>
		<a href="http://dev.mysql.com/doc/refman/${major_version}/en/server-system-variables.html">http://dev.mysql.com/doc/refman/${major_version}/en/server-system-variables.html</a>
	<#else>
		<strong class="danger">UNSUPPORTED MYSQL VERSION</strong>
	</#if>
	</p>

	<p>
		<strong color="primary">
			Visit http://www.mysql.com/products/enterprise/advisors.html<br/>
			for info about MySQL's Enterprise Monitoring and Advisory Service
		</strong>
	</p>

	<h2>SLOW QUERIES</h2>
	<#if slow_query_log = "ON">
		<span class="success">The slow query log is enabled.</span>
	<#elseif slow_query_log = "OFF">
		<span class="warning"><The slow query log is NOT enabled.</span>
	<#else>
		<span class="danger">Error: ${slow_query_log}</span>
	</#if>

	<br/>
	Current long_query_time = ${long_query_time} sec.<br/>
	You have <span class="danger">${Slow_queries}</span> out of <span class="danger">${Questions}</span> that take longer than ${long_query_time} sec. to complete

	<br/>
	<#if long_query_time?number?long gt 5>
		<span class="danger">Your long_query_time may be too high, I typically set this under 5 sec.</span>
	<#else>
		<span class="success">Your long_query_time seems to be fine</span>
	</#if>

	<h2>BINARY UPDATE LOG</h2>

		</div>
	</body>
</html>