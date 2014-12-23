<#macro humanReadableSize size>
  <#if size gte 1073741824>
	${(size / 1073741824)?string["0.##"]}G
  <#elseif size gte 1048576>
  	${(size / 1048576)?string["0.##"]}M
  <#elseif size gte 1024>
  	${(size / 1024)?string["0.##"]}K
  <#else>
  	${size?string.number}bytes
  </#if>
</#macro>
<#macro humanReadableTime seconds>
	<#assign d=(seconds / 86400) />
	<#assign h=(seconds / 3600 % 24) />
	<#assign m=(seconds / 60 % 60) />
	<#assign s=(seconds % 60) />
	${d?string["00"]} days ${h?string["00"]} hours ${m?string["00"]} minutes ${s?string["00"]} seconds
</#macro>
<html>
	<head>
		<style type="text/css">
			body{
				font-family: 'Courier New', sans-serrif;
			}
			.danger{ color: #d9534f; }
			.warning{ color: #f0ad4e; }			
			.primary{ color: #428bca; }
			.success{color: #5cb85c; }
		</style>
	</head>
	<body>
	MySQL performance tuning primer script<br/>
	Writen by: Matthew Montgomery<br/>
	Porting by: Matteo Piccinini<br/>
	<a href="https://launchpad.net/mysql-tuning-primer">https://launchpad.net/mysql-tuning-primer</a><br/>
	Licenced under GPLv2<br/>
			<p>
			<#if mysql_version_num lt 050500>
				<span class="danger">
				MySQL Version ${version} ${version_compile_machine} ${version_compile_os}
				is EOL please upgrade to MySQL 5.5 or later
				</span>
			<#else>
				MySQL Version ${version} ${version_compile_machine} ${version_compile_os}
			</#if>
			<br/>

			Uptime <@humanReadableTime seconds=(Uptime?number?long) /><br/>
			Average qps ${Questions?number?long / Uptime?number?long}<br/>
			Questions ${Questions?number?long?string.number}<br/>
			Threads Connected ${Threads_connected}
			</p>

			<#if Uptime?number?long gt 172800>
			  Server has been running for over 48hrs.<br/>
			  It should be safe to follow these recommendations
			<#else>
				<span class="danger">
					Warning: Server has not been running for at least 48hrs.
					It may not be safe to use these recommendations
				</span> 
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
					Visit <a href="http://www.mysql.com/products/enterprise/advisors.html">http://www.mysql.com/products/enterprise/advisors.html</a><br/>
					for info about MySQL's Enterprise Monitoring and Advisory Service
				</strong>
			</p>

			<h2>Slow Queries</h2>
			<#if slow_query_log = "ON">
				The slow query log is enabled.
			<#elseif slow_query_log = "OFF">
				The slow query log is <span class="danger">NOT</span> enabled.
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

			<h2>Binary Update Log</h2>
			<#if log_bin = "ON">
				The binary update log is enabled<br/>
			
				<#if max_binlog_size??><#else>
				<span class="danger">The max_binlog_size is not set. The binary log will rotate when it reaches 1GB.</span><br/>
				</#if>
			
				<#if expire_logs_days?number?long = 0>
					<span class="danger">
					The expire_logs_days is not set.<br/>
					The mysqld will retain the entire binary log until RESET MASTER or PURGE MASTER LOGS commands are run manually<br/>
					</span>
					<span class="warning">Setting expire_logs_days will allow you to remove old binary logs automatically<br/>
					See http://dev.mysql.com/doc/refman/${major_version}/en/purge-master-logs.html<br/>
					</span>
				</#if>
			
				<#if sync_binlog?number?long = 0>	
					<span class="danger">Binlog sync is not enabled, you could loose binlog records during a server crash</span><br/>
				</#if>
			<#else>
				The binary update log is <span class="danger">NOT</span> enabled.<br/>
				<span class="danger">You will not be able to do point in time recovery</span><br/>
				<span class="warning">See http://dev.mysql.com/doc/refman/${major_version}/en/point-in-time-recovery.html</span>
			</#if>


			<h2>Max Connections</h2>
			Current max_connections ${max_connections}<br/>
			Current threads_connected ${Threads_connected}<br/>
			Historic max_used_connections ${Max_used_connections}<br/>
			
			<#assign connections_ratio = ((Max_used_connections?number?long * 100) / max_connections?number?long)>
			<#if connections_ratio gte 85>
				<span class="danger">
				The number of used connections is ${connections_ratio}% of the configured maximum.<br/>
				You should raise max_connections<br/>
				</span>
			<#elseif connections_ratio lte 10>
				<span class="danger">
				The number of used connections is ${connections_ratio}% of the configured maximum.<br/>
				You are using less than 10% of your configured max_connections.<br/>
				Lowering max_connections could help to avoid an over-allocation of memory<br/>
				See "MEMORY USAGE" section to make sure you are not over-allocating<br/>
				</span>
			<#else>
				<span class="success">Your max_connections variable seems to be fine.</span><br/>
			</#if>


			<h2>Worker Threads</h2>
			<#assign historic_threads_per_sec = (threads_created1?number?long / Uptime?number?long)>
			<#assign current_threads_per_sec = (threads_created2?number?long - threads_created1?number?long)>
			Current thread_cache_size = ${thread_cache_size}<br/>
			Current threads_cached = ${Threads_cached}<br/>
			Current threads_per_sec = ${ current_threads_per_sec }<br/>
			Historic threads_per_sec = ${ historic_threads_per_sec }<br/>
			
			<#if historic_threads_per_sec gt 2 && Threads_cached?number?long lte 1>
				<span class="danger">
					Threads created per/sec are overrunning threads cached<br/>
					You should raise thread_cache_size<br/>
				</span>
			<#elseif current_threads_per_sec gt 2>
				<span class="danger">
					Threads created per/sec are overrunning threads cached<br/>
					You should raise thread_cache_size<br/>
				</span>
			<#else>
				<span class="success">Your thread_cache_size is fine</span><br/>
			</#if>

			<h2>Key Buffer</h2>
			<#assign key_read_requests=(Key_read_requests?number?long)/>
			<#assign key_reads=(Key_reads?number?long)/>
			<#assign key_blocks_unused=(Key_blocks_unused?number?long)/>
			<#assign key_cache_block_size=(key_cache_block_size?number?long)/>
			<#assign key_buffer_size=(key_buffer_size?number?long) />
			<#if key_reads = 0>
				<span class="danger">
					No key reads?!<br/>
					Seriously look into using some indexes
					<#assign key_cache_miss_rate=0/>
					<#assign key_buffer_free=(key_blocks_unused * key_cache_block_size / key_buffer_size * 100)/>
					<#assign key_buffer_freeRND=(key_buffer_free / 1)/>
				</span>
			<#else>
				<#assign key_cache_miss_rate=(key_read_requests / key_reads)/>
				<#assign key_buffer_free=(key_blocks_unused * key_cache_block_size / key_buffer_size * 100)/>
				<#assign key_buffer_freeRND=(key_buffer_free / 1)/>
				<!--
				if [ ! -z $key_blocks_unused ] ; then
					key_buffer_free=$(echo "$key_blocks_unused * $key_cache_block_size / $key_buffer_size * 100" | bc -l )
                	key_buffer_freeRND=$(echo "scale=0; $key_buffer_free / 1" | bc -l)
                else
                        key_buffer_free='Unknown'
                        key_buffer_freeRND=75
                fi
                -->
			</#if>

			<#if myisam_indexes??>
			Current MyISAM index space = <@humanReadableSize size=myisam_indexes /><br/>
			</#if>
			Current key_buffer_size = <@humanReadableSize size=key_buffer_size /><br/>
			Key cache miss rate is 1:${key_cache_miss_rate}<br/>
			Key buffer free ratio = ${key_buffer_freeRND} %<br/> 


			<#if major_version = "5.1" && mysql_version_num lt 050123>
				<#if key_buffer_size gte 4294967296 && version_compile_machine?matches("x86_64|ppc64|ia64|sparc64|i686")>
					<span class="danger">
						Using key_buffer_size > 4GB will cause instability in versions prior to 5.1.23<br/> 
						See Bug#5731, Bug#29419, Bug#29446
					</span>
				</#if>
			<#elseif major_version="5.0" && mysql_version_num lt 050052>
				<#if key_buffer_size gte 4294967296 && version_compile_machine?matches("x86_64|ppc64|ia64|sparc64|i686")>
					<span class="danger">
						Using key_buffer_size > 4GB will cause instability in versions prior to 5.0.52<br/> 
						See Bug#5731, Bug#29419, Bug#29446
					</span>
				</#if>
			<#elseif (major_version="4.1" || major_version="4.0")>
				<#if key_buffer_size gte 4294967296 && version_compile_machine?matches("x86_64|ppc64|ia64|sparc64|i686")>
					<span class="danger">
						Using key_buffer_size > 4GB will cause instability in versions prior to 5.0.52<br/>
						Reduce key_buffer_size to a safe value
						See Bug#5731, Bug#29419, Bug#29446
					</span>
				</#if>
			</#if>

			<br/>

			<#if key_cache_miss_rate lte 100 && key_cache_miss_rate gt 0 && key_buffer_freeRND lte 20>
				<span class="danger">
					You could increase key_buffer_size<br/>
					It is safe to raise this up to 1/4 of total system memory;
					assuming this is a dedicated database server.
				</span>
			<#elseif key_buffer_freeRND lte 20 && key_buffer_size lte myisam_indexes>
				<span class="danger">
					You could increase key_buffer_size<br/>
					It is safe to raise this up to 1/4 of total system memory;
					assuming this is a dedicated database server.
				</span>
			<#elseif key_cache_miss_rate gte 10000 || key_buffer_freeRND lte 50>
				<span class="danger">
					Your key_buffer_size seems to be too high. 
					Perhaps you can use these resources elsewhere
				</span>
			<#else>
				<span class="success">Your key_buffer_size seems to be fine</span>
			</#if>

			<h2>Memory Usage</h2>
<#assign max_memory_usage = (
	key_buffer_size
	+ query_cache_size?number?long
	+ tmp_table_size?number?long
	+ innodb_buffer_pool_size?number?long
	+ innodb_additional_mem_pool_size?number?long
	+ innodb_log_buffer_size?number?long
	+ max_connections?number?long * (
		sort_buffer_size?number?long
		+ read_buffer_size?number?long
		+ read_rnd_buffer_size?number?long
		+ join_buffer_size?number?long
		+ thread_stack?number?long
		+ binlog_cache_size?number?long
))/>

Maximum memory usage = <@humanReadableSize size=max_memory_usage />


	</body>
</html>
