<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<base href="http://cms.elian.cc:80/">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=9">
		<title>ҽ���� �����б�</title>
		<meta name="Keywords" content="ҽ���� �����б�">
		<meta name="Description" content="ҽ���� �����б�">
		<link rel="stylesheet" href="css/front/style/public.css" type="text/css">
		<link rel="stylesheet" href="css/front/style/global.css" type="text/css">
		<link rel="stylesheet" href="css/front/style/search_result.css" type="text/css">
		<link rel="shortcut icon" type="image/x-icon" href="http://www.elian.cc/favicon.ico">
		<script type="text/javascript">
			function changeType(cur, type){
				var childs = document.getElementById("typeDiv").childNodes;
				for(var i = 0; i < childs.length; i++){
					childs[i].className="";
				}
				
				document.getElementById("type").value = type;
				cur.className = "cur";
			}
			
			function submitSearch(form){
				var value = document.getElementById("keyword").value;
				if(value=='������ؼ���'){
					document.getElementById("keyword").value = "";
				}
				document.getElementById(form).submit();
			}
		</script>
	</head>
	<body>
		<div class="section">
			<div class="section_main">
				<div class="search_tab_nav">
					<ul class="list">
						<li>
							<a href="http://localhost:8081/ElianCms/front/searchInformation!list.action">����Ѷ</a>
						</li>
						<li>
							<a href="http://localhost:8081/ElianCms/front/searchHospital!list.action">��ҽԺ</a>
						</li>
						<li  class="cur">
							<a href="http://localhost:8081/ElianCms/front/searchDoctor!list.action">��ҽ��</a>
						</li>
						<li>
							<a href="#">�Ѽ���</a>
						</li>
						<li>
							<a href="#">��ҩƷ</a>
						</li>
					</ul>
				</div>
				<form id="searchDoctorForm" method="post" action="front/searchDoctor!search.action">
					<div class="wrap_key_select">
						<div class="search_result_tips">
							���ѵ�
							<em class="em">0</em>��������������Ľ��
						</div>
						<div class="key_select">
							<div class="i"> <span class="tit">����ѡ�</span>
		                        <div id="typeDiv" class="details">
		                        	<input id="type" type="hidden" name="type" value="">
		                        	<a href="javascript:void(0);" class="cur" onclick="changeType(this, '')">����</a>
		                        	<a href="javascript:void(0);" class="" onclick="changeType(this, 'name')">���</a>
		                        	<a href="javascript:void(0);" class="" onclick="changeType(this, 'specility')">ר��</a>
		                        </div>
		                    </div>
							<div class="last">
								<div class="wrap_search">
									<div class="search">
										<div class="wrap_ipt">
											<input id="keyword" name="keyword" onfocus="if (this.value=='������ؼ���'){this.value='';this.style.color='#333'}"
												type="text" class="ipt" value="������ؼ���" />
										</div>
										<input onclick="submitSearch('searchDoctorForm')" class="ips" value="����" />
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="wrap_bd clearfix">
						<div class="wrap_ui_tit">
							<h3 class="ui_tit">
								����ҽ��
							</h3>
						</div>
						<div class="mod_list">
							<ul class="pt03">
								
							</ul>
						</div>
						<h4 align="center" style="display:block">���ź���û���ҵ�����Ҫ�ģ������ؼ�������ԣ�</h4>
						

<script type="text/javascript">
			function toPage(page){
				if (page != null && page != ''){
					document.getElementById("currentPage").value= page;
				    document.forms[0].submit();
				    }
			}
			
			function go(pNo, currentPage) {
				var pageNo = document.getElementById(pNo);
				if (Number(pageNo.value) == Number(currentPage))
					return;
				toPage(pageNo.value);
			}
			
			function numberFormat(pageNo, lastPage) {
				var idNum = document.getElementById(pageNo);
				if (!Number(idNum.value) || idNum.value == 0 || idNum.value == null || idNum.value == undefined) {
					idNum.value = 1;
				}
				else {
					if (Number(idNum.value) > Number(lastPage)) {
						idNum.value = lastPage;
					}
				}
				idNum.value = Number(idNum.value);
			}
			
			function checkEnter(event, clickId) {
	����    		if(event.keyCode == 13){
	����    			document.getElementById(clickId).click();
	����    		}
	����    	}
	</script>
	<input id="currentPage" name="pagination.pageNo" type="hidden" value="1"/>
	<ul id='pagination-digg' class='pagination clearfix' style='float: right'>
		
		
		
		
		
		
		 	
		
		 	
		
		 	
		
		
		
		<li class='page'>
			<input type='text' id='pageNo' class='ipage size2' title='������ҳ��' value='1' 
				onkeydown="checkEnter(event, 'igo');" onkeyup="numberFormat('pageNo',0);"/>
			<span class="page-count">
				&nbsp;/1
			</span>
		</li>
		<li title='��תҳ��' class='go'>
			<a id='igo' class='igo' onclick="go('pageNo',1);">GO</a>
		</li>
	</ul>
					</div>
				</form>
			</div>
			<div class="sidebar article_sidebar">
				<div class="aside">
					<a href="#"><img src="http://placehold.it/280x200" width="280"
							height="200" /> </a>
				</div>
				<div class="aside mod-rank">
					<div class="mod_gray_hd">
						<h3 class="ico_hand">
							�������
						</h3>
					</div>
					<div class="mod_gray_bd">
						<div class="aside-bd">
							<ul class="list">
								<li class="first">
									<div class="tit">
										<b class="top-rank">1</b><a href="">�������Խ��ӻᶳ�����</a>
									</div>
									<div class="pt03">
										<a href="#" class="pic"> <img src="images/front/pic100x75.jpg"
												width="100" height="75" /> </a>
										<p class="txt">
											���׽��Ӿ²˽����ܲ����������հ����͵��������β��ܰ���������Ͱ�
										</p>
									</div>
								</li>
								<li>
									<div class="tit">
										<b class="top-rank">2</b><a href="">�������Խ��ӻᶳ�����</a>
									</div>
								</li>
								<li>
									<div class="tit">
										<b class="top-rank">3</b><a href="">�������Խ��ӻᶳ�����</a>
									</div>
								</li>
								<li>
									<div class="tit">
										<b>4</b><a href="">�������Խ��ӻᶳ�����</a>
									</div>
								</li>
								<li>
									<div class="tit">
										<b>5</b><a href="">�������Խ��ӻᶳ�����</a>
									</div>
								</li>
								<li>
									<div class="tit">
										<b>6</b><a href="">�������Խ��ӻᶳ�����</a>
									</div>
								</li>
								<li>
									<div class="tit">
										<b>7</b><a href="">�������Խ��ӻᶳ�����</a>
									</div>
								</li>
							</ul>
						</div>
					</div>
				</div>
				<div class="aside">
					<div class="mod_recomm_hd">
						<h3 class="ui_tit">
							�Ƽ�ҽ��
						</h3>
						<a href="#" class="more">&gt;&gt;���</a>
					</div>
					<div class="mod_recomm_bd">
						<ul class="pt02">
							<li>
								<a class="pic" target="_blank"
									href="http://h.elian.cc/index.jsp?h=516"><img
										src="images/front/pic70x90.jpg" width="70" height="90" alt="">
								</a>
								<div class="txt">
									<span class="tit"><a target="_blank" href="">�Ž���</a> </span>
									<span>�ڿ�</span>
									<span class="ico-tel">����ҽʦ</span>
									<a href="#" class="link_btn">����鿴</a>
								</div>
							</li>
							<li>
								<a class="pic" target="_blank"
									href="http://h.elian.cc/index.jsp?h=516"><img
										src="images/front/pic70x90.jpg" width="70" height="90" alt="">
								</a>
								<div class="txt">
									<span class="tit"><a target="_blank" href="">�Ž���</a> </span>
									<span>�ڿ�</span>
									<span class="ico-tel">����ҽʦ</span>
									<a href="#" class="link_btn">����鿴</a>
								</div>
							</li>
							<li>
								<a class="pic" target="_blank"
									href="http://h.elian.cc/index.jsp?h=516"><img
										src="images/front/pic70x90.jpg" width="70" height="90" alt="">
								</a>
								<div class="txt">
									<span class="tit"><a target="_blank" href="">�Ž���</a> </span>
									<span>�ڿ�</span>
									<span class="ico-tel">����ҽʦ</span>
									<a href="#" class="link_btn">����鿴</a>
								</div>
							</li>
							<li>
								<a class="pic" target="_blank"
									href="http://h.elian.cc/index.jsp?h=516"><img
										src="images/front/pic70x90.jpg" width="70" height="90" alt="">
								</a>
								<div class="txt">
									<span class="tit"><a target="_blank" href="">�Ž���</a> </span>
									<span>�ڿ�</span>
									<span class="ico-tel">����ҽʦ</span>
									<a href="#" class="link_btn">����鿴</a>
								</div>
							</li>
						</ul>
						<div class="line"></div>
					</div>
				</div>
				<div class="aside">
					<div class="mod_gray_hd">
						<h3>
							����ҽ��
						</h3>
					</div>
					<div class="mod_gray_bd pt02 high_rank_doc">
						<a class="pic" target="_blank"
							href="http://h.elian.cc/index.jsp?h=516"><img
								src="images/front/pic100x130.jpg" width="100" height="130" alt="">
						</a>
						<div class="txt">
							<div class="i">
								<span class="tit">����</span><span>������</span>
							</div>
							<div class="i">
								<span class="tit">���ң�</span><span>�п�</span>
							</div>
							<div class="i">
								<span class="tit">ҽԺ��</span><span>��ɽ����ר��ҽԺ</span>
							</div>
							<div class="i">
								<span class="tit">�ۺ����֣�</span><span>4.5��</span>
							</div>
							<div class="i">
								<a class="zixun_btn" href="#">��ѯҽ��</a>
								<a class="guahao_btn" href="#">���ϹҺ�</a>
							</div>
						</div>
					</div>
				</div>
				<div class="aside aside_recomm_hos">
					<div class="mod_recomm_hd">
						<h3 class="ui_tit">
							�Ƽ�ҽԺ
						</h3>
						<a href="#" class="more">&gt;&gt;���</a>
					</div>
					<div class="mod_recomm_bd">
						<ul class="pt01 clearfix">
							<li>
								<a class="pic" target="_blank"
									href="http://h.elian.cc/index.jsp?h=516"><img
										src="images/front/hos120x90.jpg" width="120" height="90" alt="">
								</a>
								<div class="tit">
									<a href="#">��ɽ�оŽ�ҽԺ</a>
								</div>
							</li>
							<li>
								<a class="pic" target="_blank"
									href="http://h.elian.cc/index.jsp?h=516"><img
										src="images/front/hos120x90.jpg" width="120" height="90" alt="">
								</a>
								<div class="tit">
									<a href="#">��ɽ�оŽ�ҽԺ</a>
								</div>
							</li>
							<li>
								<a class="pic" target="_blank"
									href="http://h.elian.cc/index.jsp?h=516"><img
										src="images/front/hos120x90.jpg" width="120" height="90" alt="">
								</a>
								<div class="tit">
									<a href="#">��ɽ�оŽ�ҽԺ</a>
								</div>
							</li>
							<li>
								<a class="pic" target="_blank"
									href="http://h.elian.cc/index.jsp?h=516"><img
										src="images/front/hos120x90.jpg" width="120" height="90" alt="">
								</a>
								<div class="tit">
									<a href="#">��ɽ�оŽ�ҽԺ</a>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="footer">
			<div class="tips">
				<p>
					��ܰ��ʾ����������Լ���ĳ�ֽ������⣬��������ѯר�һ򾡿�ȥҽԺ�������ơ�
				</p>
			</div>
			<div class="bottom_nav">
				<a href="http://www.elian.cc/other/about_intro.html" target="_blank">��������</a>
				|
				<a href="http://www.elian.cc/other/about_sitemap.html"
					target="_blank">��վ��ͼ</a> |
				<a href="http://www.elian.cc/about/index.html" target="_blank">��������</a>
				|
				<a href="">Ӫ������</a> |
				<a href="http://www.elian.cc/other/about_law.html" target="_blank">��������</a>
				|
				<a href="">��˽����</a> |
				<a href="http://www.elian.cc/other/about_contact.html"
					target="_blank">��ϵ����</a> |
				<a href="http://www.elian.cc/other/about_vip.html" target="_blank">����ҽ�����ƹ�</a>
			</div>
			<p>
				�绰��0757-82137888 ���棺0757-82139888 ȫ��绰��40001-91580
			</p>
			<p>
				<a href="http://www.elian.cc/">www.elian.cc </a>
				<a href="http://www.xn--ekrw93hgwa.com/">ҽ����.COM</a>
				<a href="http://www.xn--ekrw93hgwa.cn/">ҽ����.CN</a> ��Ȩ����δ����Ȩ����ת��
			</p>
			<p>
				��ICP��10054714��
				<a
					href="http://www.elian.cc/about/images/photo/DrugInformationCertificate.jpg">������ҩƷ��Ϣ�����ʸ�֤��</a>
				֤����ţ�
				<a
					href="http://www.elian.cc/about/images/photo/DrugInformationCertificate.jpg"
					target="_blank">������-�Ǿ�Ӫ��-2010-0176</a>
			</p>
			<p>
				��վ��Ϣ�����ο�������Ϊ��ϼ�ҽ�Ƶ����ݣ�����ת�ػ����������漰��Ȩ����������������ϵ��
			</p>
		</div>
		<script type="text/javascript" src="script/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="script/jquery.header.js"></script>
		<div class="hidden">
			<script type="text/javascript" src="script/baidu-flow.js"></script>
			<script type="text/javascript" src="script/google-flow.js"></script>
			<a href="http://www.cnzz.com/stat/website.php?web_id=3321260"
				target="_blank" title="վ��ͳ��">վ��ͳ��</a>
			<script type="text/javascript">
				var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://"
						: " http://");
				document.write(unescape("%3Cscript src='"
								+ _bdhmProtocol
								+ "hm.baidu.com/h.js%3Ffcab8f29a9e279175c4fd1b3038becb3' type='text/javascript'%3E%3C/script%3E"));
			</script>
		</div>
	</body>
</html>