(function(f) {
	if (window.KindEditor === f) {
		var c = {};
		c.version = "3.5.4 (2011-05-01)";
		c.scriptPath = function() {
			for ( var a = document.getElementsByTagName("script"), b = 0, d = a.length; b < d; b++) {
				var e = a[b].src;
				if (e && e.match(/kindeditor[\w\-\.]*\.js/))
					return e.substring(0, e.lastIndexOf("/") + 1)
			}
			return ""
		}();
		c.browser = function() {
			var a = navigator.userAgent.toLowerCase();
			return {
				VERSION : a.match(/(msie|firefox|webkit|opera)[\/:\s](\d+)/) ? RegExp.$2
						: "0",
				IE : a.indexOf("msie") > -1 && a.indexOf("opera") == -1,
				GECKO : a.indexOf("gecko") > -1 && a.indexOf("khtml") == -1,
				WEBKIT : a.indexOf("applewebkit") > -1,
				OPERA : a.indexOf("opera") > -1
			}
		}();
		c.setting = {
			wyswygMode : true,
			loadStyleMode : true,
			resizeMode : 2,
			filterMode : false,
			autoSetDataMode : false,
			shadowMode : true,
			useContextmenu : true,
			urlType : "",
			skinType : "default",
			syncType : "form",
			newlineTag : "p",
			dialogAlignType : "page",
			cssPath : "",
			skinsPath : c.scriptPath + "skins/",
			pluginsPath : c.scriptPath + "plugins/",
			minWidth : 200,
			minHeight : 100,
			minChangeSize : 5,
			toolbarLineHeight : 24,
			statusbarHeight : 11,
			items : [ "source", "|", "fullscreen", "undo", "redo", "print",
					"cut", "copy", "paste", "plainpaste", "wordpaste", "|",
					"justifyleft", "justifycenter", "justifyright",
					"justifyfull", "insertorderedlist", "insertunorderedlist",
					"indent", "outdent", "subscript", "superscript", "|",
					"selectall", "-", "title", "fontname", "fontsize", "|",
					"textcolor", "bgcolor", "bold", "italic", "underline",
					"strikethrough", "removeformat", "|", "image", "flash",
					"media", "advtable", "hr", "emoticons", "link", "unlink",
					"|", "about" ],
			colorTable : [
					[ "#E53333", "#E56600", "#FF9900", "#64451D", "#DFC5A4",
							"#FFE500" ],
					[ "#009900", "#006600", "#99BB00", "#B8D100", "#60D978",
							"#00D5FF" ],
					[ "#337FE5", "#003399", "#4C33E5", "#9933E5", "#CC33E5",
							"#EE33EE" ],
					[ "#FFFFFF", "#CCCCCC", "#999999", "#666666", "#333333",
							"#000000" ] ],
			noEndTags : [ "br", "hr", "img", "area", "col", "embed", "input",
					"param" ],
			inlineTags : [ "b", "del", "em", "font", "i", "span", "strike",
					"strong", "sub", "sup", "u" ],
			endlineTags : [ "br", "hr", "table", "tbody", "td", "tr", "th",
					"div", "p", "ol", "ul", "li", "blockquote", "h1", "h2",
					"h3", "h4", "h5", "h6", "script", "style", "marquee" ],
			htmlTags : {
				font : [ "color", "size", "face", ".background-color" ],
				span : [ ".color", ".background-color", ".font-size",
						".font-family", ".background", ".font-weight",
						".font-style", ".text-decoration", ".vertical-align" ],
				div : [ "align", ".border", ".margin", ".padding",
						".text-align", ".color", ".background-color",
						".font-size", ".font-family", ".font-weight",
						".background", ".font-style", ".text-decoration",
						".vertical-align", ".margin-left" ],
				table : [ "border", "cellspacing", "cellpadding", "width",
						"height", "align", "bordercolor", ".padding",
						".margin", ".border", "bgcolor", ".text-align",
						".color", ".background-color", ".font-size",
						".font-family", ".font-weight", ".font-style",
						".text-decoration", ".background", ".width", ".height" ],
				"td,th" : [ "align", "valign", "width", "height", "colspan",
						"rowspan", "bgcolor", ".text-align", ".color",
						".background-color", ".font-size", ".font-family",
						".font-weight", ".font-style", ".text-decoration",
						".vertical-align", ".background" ],
				a : [ "href", "target", "name" ],
				embed : [ "src", "width", "height", "type", "loop",
						"autostart", "quality", ".width", ".height", "align",
						"allowscriptaccess", "/" ],
				img : [ "src", "width", "height", "border", "alt", "title",
						".width", ".height", "/" ],
				hr : [ "/" ],
				br : [ "/" ],
				"p,ol,ul,li,blockquote,h1,h2,h3,h4,h5,h6" : [ "align",
						".text-align", ".color", ".background-color",
						".font-size", ".font-family", ".background",
						".font-weight", ".font-style", ".text-decoration",
						".vertical-align", ".text-indent", ".margin-left" ],
				"tbody,tr,strong,b,sub,sup,em,i,u,strike" : []
			},
			mediaTypes : {
				rm : "audio/x-pn-realaudio-plugin",
				flash : "application/x-shockwave-flash",
				media : "video/x-ms-asf-plugin"
			},
			afterTab : function(a) {
				c.util.setSelection(a);
				c.util.insertHtml(a, "&nbsp;&nbsp;&nbsp;&nbsp;")
			}
		};
		c.g = {};
		c.plugin = {};
		c.$ = function(a, b) {
			return (b || document).getElementById(a)
		};
		c.$$ = function(a, b) {
			return (b || document).createElement(a)
		};
		c.event = {
			add : function(a, b, d, e) {
				if (a.addEventListener)
					a.addEventListener(b, d, false);
				else
					a.attachEvent && a.attachEvent("on" + b, d);
				e !== f && c.g[e].eventStack.push( {
					el : a,
					type : b,
					fn : d
				})
			},
			remove : function(a, b, d, e) {
				if (a.removeEventListener)
					a.removeEventListener(b, d, false);
				else
					a.detachEvent && a.detachEvent("on" + b, d);
				if (e !== f) {
					e = c.g[e].eventStack;
					for ( var g = 0, i = e.length; g < i; g++) {
						var j = e[g];
						j && a === j.el && b === j.type && d === j.fn
								&& delete e[g]
					}
				}
			},
			stopPropagation : function(a) {
				a.stopPropagation && a.stopPropagation();
				if (a.cancelBubble !== f)
					a.cancelBubble = true
			},
			preventDefault : function(a) {
				a.preventDefault && a.preventDefault();
				if (a.returnValue !== f)
					a.returnValue = false
			},
			stop : function(a) {
				this.stopPropagation(a);
				this.preventDefault(a)
			},
			bind : function(a, b, d, e) {
				this.add(a, b, function(g) {
					d(g);
					c.event.stop(g);
					return false
				}, e)
			},
			input : function(a, b, d) {
				function e(g) {
					window.setTimeout(function() {
						b(g)
					}, 1)
				}
				this.add(a, "keyup", function(g) {
					if (!g.ctrlKey && !g.altKey
							&& (g.keyCode < 16 || g.keyCode > 18)
							&& g.keyCode != 116) {
						b(g);
						c.event.stop(g);
						return false
					}
				}, d);
				a = a.nodeName == "#document" ? a.body : a;
				this.add(a, "paste", e, d);
				this.add(a, "cut", e, d)
			},
			ctrl : function(a, b, d, e) {
				b = b.toString().match(/^\d{2,}$/) ? b : b.toUpperCase()
						.charCodeAt(0);
				this.add(a, "keydown",
						function(g) {
							if (g.ctrlKey && g.keyCode == b && !g.shiftKey
									&& !g.altKey) {
								d(g);
								c.event.stop(g);
								return false
							}
						}, e)
			},
			ready : function(a, b, d, e) {
				b = b || window;
				d = d || document;
				var g = false, i = function() {
					if (!g) {
						g = true;
						a()
					}
				};
				if (d.addEventListener)
					this.add(d, "DOMContentLoaded", i, e);
				else if (d.attachEvent) {
					this.add(d, "readystatechange", function() {
						d.readyState == "complete" && i()
					}, e);
					if (d.documentElement.doScroll
							&& typeof b.frameElement === "undefined") {
						var j = function() {
							if (!g) {
								try {
									d.documentElement.doScroll("left")
								} catch (k) {
									window.setTimeout(j, 0);
									return
								}
								i()
							}
						};
						j()
					}
				}
				this.add(b, "load", i, e)
			}
		};
		c.each = function(a, b) {
			for ( var d in a)
				a.hasOwnProperty(d) && b(d, a[d])
		};
		c.eachNode = function(a, b) {
			var d = function(e) {
				if (c.util.getNodeType(e) != 1)
					return true;
				for (e = e.firstChild; e;) {
					var g = e.nextSibling;
					if (!b(e))
						return false;
					if (!d(e))
						return false;
					e = g
				}
				return true
			};
			d(a)
		};
		c.selection = function(a) {
			this.keRange = this.range = this.sel = null;
			this.isControl = false;
			var b = a.parentWindow || a.defaultView;
			this.init = function() {
				var d = a.selection ? a.selection : b.getSelection(), e;
				try {
					e = d.rangeCount > 0 ? d.getRangeAt(0) : d.createRange()
				} catch (g) {
				}
				e || (e = c.util.createRange(a));
				this.sel = d;
				this.range = e;
				var i, j, k;
				if (c.browser.IE)
					if (e.item) {
						this.isControl = true;
						d = j = e.item(0);
						i = k = 0
					} else {
						this.isControl = false;
						d = function(m) {
							var p = e.duplicate();
							p.collapse(m);
							var o = p.parentElement(), n = o.childNodes;
							if (n.length == 0)
								return {
									node : o,
									pos : 0
								};
							var r, s = 0, t = false, q = e.duplicate();
							c.util.moveToElementText(q, o);
							for ( var w = 0, u = n.length; w < u; w++) {
								m = n[w];
								var x = q.compareEndPoints("StartToStart", p);
								if (x > 0)
									t = true;
								else if (x == 0)
									if (m.nodeType == 1) {
										p = new c.range(a);
										p.selectTextNode(m);
										return {
											node : p.startNode,
											pos : 0
										}
									} else
										return {
											node : m,
											pos : 0
										};
								if (m.nodeType == 1) {
									x = e.duplicate();
									c.util.moveToElementText(x, m);
									q.setEndPoint("StartToEnd", x);
									if (t)
										s += x.text.replace(/\r\n|\n|\r/g, "").length;
									else
										s = 0
								} else if (m.nodeType == 3)
									if (typeof m.nodeValue === "string") {
										q.moveStart("character",
												m.nodeValue.length);
										s += m.nodeValue.length
									}
								t || (r = m)
							}
							if (!t && r.nodeType == 1) {
								r = o.lastChild;
								return {
									node : r,
									pos : r.nodeType == 1 ? 1
											: r.nodeValue.length
								}
							}
							q = e.duplicate();
							c.util.moveToElementText(q, o);
							q.setEndPoint("StartToEnd", p);
							s -= q.text.replace(/\r\n|\n|\r/g, "").length;
							return {
								node : r,
								pos : s
							}
						};
						i = d(true);
						k = d(false);
						d = i.node;
						i = i.pos;
						j = k.node;
						k = k.pos
					}
				else {
					d = e.startContainer;
					i = e.startOffset;
					j = e.endContainer;
					k = e.endOffset;
					if (d.nodeType == 1
							&& typeof d.childNodes[i] != "undefined") {
						d = d.childNodes[i];
						i = 0
					}
					if (j.nodeType == 1) {
						k = k == 0 ? 1 : k;
						if (typeof j.childNodes[k - 1] != "undefined") {
							j = j.childNodes[k - 1];
							k = j.nodeType == 1 ? 0 : j.nodeValue.length
						}
					}
					this.isControl = d.nodeType == 1 && d === j
							&& e.startOffset + 1 == e.endOffset;
					if (d.nodeType == 1 && j.nodeType == 3 && k == 0
							&& j.previousSibling)
						for ( var l = j.previousSibling; l;) {
							if (l === d) {
								j = d;
								break
							}
							if (l.childNodes.length != 1)
								break;
							l = l.childNodes[0]
						}
					if (e.collapsed) {
						l = new c.range(a);
						l.setTextStart(d, i);
						j = l.startNode;
						k = l.startPos
					}
				}
				l = new c.range(a);
				l.setTextStart(d, i);
				l.setTextEnd(j, k);
				this.keRange = l
			};
			this.init();
			this.addRange = function(d) {
				if (!(c.browser.GECKO && c.browser.VERSION < 3)) {
					this.keRange = d;
					if (c.browser.IE) {
						var e = function(k) {
							var l = c.util.createRange(a), m = k ? d.startNode
									: d.endNode;
							if (m.nodeType == 1) {
								c.util.moveToElementText(l, m);
								l.collapse(k)
							} else if (m.nodeType == 3) {
								l = c.util.getNodeStartRange(a, m);
								l.moveStart("character", k ? d.startPos
										: d.endPos)
							}
							return l
						};
						if (!this.range.item) {
							var g = d.startNode;
							if (g == d.endNode && c.util.getNodeType(g) == 1
									&& c.util.getNodeTextLength(g) == 0) {
								e = a.createTextNode(" ");
								g.appendChild(e);
								c.util.moveToElementText(this.range, g);
								this.range.collapse(false);
								this.range.select();
								g.removeChild(e)
							} else {
								if (g.nodeType == 3 && d.collapsed()) {
									this.range = e(true);
									this.range.collapse(true)
								} else {
									this.range.setEndPoint("StartToStart",
											e(true));
									this.range.setEndPoint("EndToStart",
											e(false))
								}
								this.range.select()
							}
						}
					} else {
						g = function(k) {
							for ( var l = 0; k;) {
								k = k.previousSibling;
								l++
							}
							return --l
						};
						e = new c.range(a);
						e.setTextStart(d.startNode, d.startPos);
						e.setTextEnd(d.endNode, d.endPos);
						var i = e.startNode, j = e.endNode;
						c.util.getNodeType(i) == 88 ? this.range.setStart(
								i.parentNode, g(e.startNode)) : this.range
								.setStart(i, e.startPos);
						c.util.getNodeType(j) == 88 ? this.range.setEnd(
								j.parentNode, g(e.endNode) + 1) : this.range
								.setEnd(j, e.endPos);
						this.sel.removeAllRanges();
						this.sel.addRange(this.range)
					}
				}
			};
			this.focus = function() {
				c.browser.IE && this.range != null && this.range.select()
			}
		};
		c.range = function(a) {
			this.endPos = this.endNode = this.startPos = this.startNode = null;
			this.getParentElement = function() {
				var b = function(g, i) {
					for (; g
							&& (!g.tagName || g.tagName.toLowerCase() != "body");) {
						g = g.parentNode;
						if (i(g))
							return
					}
				}, d = [];
				b(this.startNode, function(g) {
					d.push(g)
				});
				var e;
				b(this.endNode, function(g) {
					if (c.util.inArray(g, d)) {
						e = g;
						return true
					}
				});
				return e ? e : a.body
			};
			this.getNodeList = function() {
				var b = this, d = this.getParentElement(), e = [], g = false;
				if (d == b.startNode)
					g = true;
				g && e.push(d);
				c.eachNode(d, function(i) {
					if (i == b.startNode)
						g = true;
					var j = new c.range(a);
					j.selectTextNode(i);
					var k = j.comparePoints("START_TO_END", b);
					if (k > 0)
						return false;
					else if (k == 0)
						if (j.startNode !== j.endNode
								|| j.startPos !== j.endPos)
							return false;
					g && e.push(i);
					return true
				});
				return e
			};
			this.comparePoints = function(b, d) {
				var e = function(g, i, j, k) {
					if (c.browser.IE) {
						var l = function(p, o, n) {
							var r = c.util.createRange(a), s = c.util
									.getNodeType(p);
							if (s == 1) {
								c.util.moveToElementText(r, p);
								r.collapse(n)
							} else if (s == 3) {
								r = c.util.getNodeStartRange(a, p);
								r.moveStart("character", o);
								r.collapse(true)
							}
							return r
						}, m;
						m = b == "START_TO_START" || b == "START_TO_END" ? l(g,
								i, true) : l(g, i, false);
						g = b == "START_TO_START" || b == "END_TO_START" ? l(j,
								k, true) : l(j, k, false);
						return m.compareEndPoints("StartToStart", g)
					} else {
						m = c.util.createRange(a);
						m.selectNode(g);
						b == "START_TO_START" || b == "START_TO_END" ? m
								.collapse(true) : m.collapse(false);
						g = c.util.createRange(a);
						g.selectNode(j);
						b == "START_TO_START" || b == "END_TO_START" ? g
								.collapse(true) : g.collapse(false);
						i = m.compareBoundaryPoints(Range.START_TO_START, g) > 0 ? 1
								: m.compareBoundaryPoints(Range.START_TO_START,
										g) == 0 ? i > k ? 1 : i == k ? 0 : -1
										: -1
					}
					return i
				};
				if (b == "START_TO_START")
					return e(this.startNode, this.startPos, d.startNode,
							d.startPos);
				if (b == "START_TO_END")
					return e(this.startNode, this.startPos, d.endNode, d.endPos);
				if (b == "END_TO_START")
					return e(this.endNode, this.endPos, d.startNode, d.startPos);
				if (b == "END_TO_END")
					return e(this.endNode, this.endPos, d.endNode, d.endPos)
			};
			this.collapsed = function() {
				return this.startNode === this.endNode
						&& this.startPos === this.endPos
			};
			this.collapse = function(b) {
				b ? this.setEnd(this.startNode, this.startPos) : this.setStart(
						this.endNode, this.endPos)
			};
			this.setTextStart = function(b, d) {
				var e = b;
				c.eachNode(b, function(g) {
					if (c.util.getNodeType(g) == 3 && g.nodeValue.length > 0
							|| c.util.getNodeType(g) == 88) {
						e = g;
						d = 0;
						return false
					}
					return true
				});
				this.setStart(e, d)
			};
			this.setStart = function(b, d) {
				this.startNode = b;
				this.startPos = d;
				if (this.endNode === null) {
					this.endNode = b;
					this.endPos = d
				}
			};
			this.setTextEnd = function(b, d) {
				var e = b;
				c.eachNode(b, function(g) {
					if (c.util.getNodeType(g) == 3 && g.nodeValue.length > 0
							|| c.util.getNodeType(g) == 88) {
						e = g;
						d = c.util.getNodeType(g) == 3 ? g.nodeValue.length : 0
					}
					return true
				});
				this.setEnd(e, d)
			};
			this.setEnd = function(b, d) {
				this.endNode = b;
				this.endPos = d;
				if (this.startNode === null) {
					this.startNode = b;
					this.startPos = d
				}
			};
			this.selectNode = function(b) {
				this.setStart(b, 0);
				this.setEnd(b, b.nodeType == 1 ? 0 : b.nodeValue.length)
			};
			this.selectTextNode = function(b) {
				this.setTextStart(b, 0);
				this.setTextEnd(b, b.nodeType == 1 ? 0 : b.nodeValue.length)
			};
			this.extractContents = function(b) {
				b = b === f ? true : b;
				var d = this, e = this.startNode, g = this.startPos, i = this.endNode, j = this.endPos, k = function(
						s, t, q) {
					var w = s.nodeValue.length, u = s.cloneNode(true)
							.splitText(t);
					u.splitText(q - t);
					if (b) {
						var x = s;
						if (t > 0)
							x = s.splitText(t);
						q < w && x.splitText(q - t);
						x.parentNode.removeChild(x)
					}
					return u
				}, l = c.util.arrayToHash(c.setting.noEndTags), m = false, p = false, o = function(
						s, t) {
					if (c.util.getNodeType(s) != 1)
						return true;
					for ( var q = s.firstChild; q;) {
						if (q == e)
							m = true;
						if (q == i)
							p = true;
						var w = q.nextSibling, u = q.nodeType;
						if (u == 1) {
							u = new c.range(a);
							u.selectNode(q);
							u = u.comparePoints("END_TO_END", d);
							if (m
									&& (u < 0 || u == 0
											&& l[q.nodeName.toLowerCase()] !== f)) {
								u = q.cloneNode(true);
								t.appendChild(u);
								b && q.parentNode.removeChild(q)
							} else {
								u = q.cloneNode(false);
								if (l[u.nodeName.toLowerCase()] === f) {
									t.appendChild(u);
									if (!o(q, u))
										return false
								}
							}
						} else if (u == 3)
							if (m)
								if (q == e && q == i) {
									q = k(q, g, j);
									t.appendChild(q);
									return false
								} else if (q == e) {
									q = k(q, g, q.nodeValue.length);
									t.appendChild(q)
								} else if (q == i) {
									q = k(q, 0, j);
									t.appendChild(q);
									return false
								} else {
									q = k(q, 0, q.nodeValue.length);
									t.appendChild(q)
								}
						q = w;
						if (p)
							return false
					}
					t.innerHTML.replace(/<.*?>/g, "") === "" && t.parentNode
							&& t.parentNode.removeChild(t);
					return true
				}, n = this.getParentElement(), r = n.cloneNode(false);
				o(n, r);
				return r
			};
			this.cloneContents = function() {
				return this.extractContents(false)
			};
			this.getText = function() {
				return this.cloneContents().innerHTML.replace(/<.*?>/g, "")
			}
		};
		c.cmd = function(a) {
			this.doc = c.g[a].iframeDoc;
			this.keSel = c.g[a].keSel;
			this.keRange = c.g[a].keRange;
			this.mergeAttributes = function(b, d) {
				for ( var e = 0, g = d.length; e < g; e++)
					c.each(d[e], function(i, j) {
						if (i.charAt(0) == ".") {
							var k = c.util.getJsKey(i.substr(1));
							b.style[k] = j
						} else {
							if (c.browser.IE && c.browser.VERSION < 8
									&& i == "class")
								i = "className";
							b.setAttribute(i, j)
						}
					});
				return b
			};
			this.wrapTextNode = function(b, d, e, g, i) {
				var j = b.nodeValue.length, k = d == 0 && e == j, l = new c.range(
						this.doc);
				l.selectTextNode(b.parentNode);
				if (k && b.parentNode.tagName == g.tagName
						&& l.comparePoints("END_TO_END", this.keRange) <= 0
						&& l.comparePoints("START_TO_START", this.keRange) >= 0) {
					this.mergeAttributes(b.parentNode, i);
					return b
				} else {
					g = g.cloneNode(true);
					if (k) {
						d = b.cloneNode(true);
						g.appendChild(d);
						b.parentNode.replaceChild(g, b);
						return d
					} else {
						k = b;
						if (d < e) {
							if (d > 0)
								k = b.splitText(d);
							e < j && k.splitText(e - d);
							d = k.cloneNode(true);
							g.appendChild(d);
							k.parentNode.replaceChild(g, k);
							return d
						} else {
							if (d < j) {
								k = b.splitText(d);
								k.parentNode.insertBefore(g, k)
							} else
								k.nextSibling ? k.parentNode.insertBefore(g,
										k.nextSibling) : k.parentNode
										.appendChild(g);
							return g
						}
					}
				}
			};
			this.wrap = function(b, d) {
				d = d || [];
				var e = this;
				this.keSel.focus();
				var g = c.$$(b, this.doc);
				this.mergeAttributes(g, d);
				var i = this.keRange, j = i.startNode, k = i.startPos, l = i.endNode, m = i.endPos, p = i
						.getParentElement();
				if (!c.util.inMarquee(p)) {
					var o = false;
					c.eachNode(p, function(n) {
						if (n == j)
							o = true;
						if (n.nodeType == 1)
							if (n == j && n == l) {
								if (c.util.inArray(n.tagName.toLowerCase(),
										c.g[a].noEndTags))
									k > 0 ? n.parentNode.appendChild(g)
											: n.parentNode.insertBefore(g, n);
								else
									n.appendChild(g);
								i.selectNode(g);
								return false
							} else if (n == j)
								i.setStart(n, 0);
							else {
								if (n == l) {
									i.setEnd(n, 0);
									return false
								}
							}
						else if (n.nodeType == 3)
							if (o)
								if (n == j && n == l) {
									n = e.wrapTextNode(n, k, m, g, d);
									i.selectNode(n);
									return false
								} else if (n == j) {
									n = e.wrapTextNode(n, k,
											n.nodeValue.length, g, d);
									i.setStart(n, 0)
								} else if (n == l) {
									n = e.wrapTextNode(n, 0, m, g, d);
									i.setEnd(n, n.nodeType == 1 ? 0
											: n.nodeValue.length);
									return false
								} else
									e.wrapTextNode(n, 0, n.nodeValue.length, g,
											d);
						return true
					});
					this.keSel.addRange(i)
				}
			};
			this.getTopParent = function(b, d) {
				for ( var e = null; d;) {
					d = d.parentNode;
					if (c.util.inArray(d.tagName.toLowerCase(), b))
						e = d;
					else
						break
				}
				return e
			};
			this.splitNodeParent = function(b, d, e) {
				var g = new c.range(this.doc);
				g.selectNode(b.firstChild);
				g.setEnd(d, e);
				d = g.extractContents();
				b.parentNode.insertBefore(d, b);
				return {
					left : d,
					right : b
				}
			};
			this.remove = function(b) {
				var d = this.keRange, e = d.startNode, g = d.startPos, i = d.endNode, j = d.endPos;
				this.keSel.focus();
				if (!c.util.inMarquee(d.getParentElement())) {
					var k = d.getText().replace(/\s+/g, "") === "";
					if (!(k && !c.browser.IE)) {
						var l = [];
						c.each(b, function(s) {
							s != "*" && l.push(s)
						});
						var m = this.getTopParent(l, e), p = this.getTopParent(
								l, i);
						if (m) {
							var o = this.splitNodeParent(m, e, g);
							d.setStart(o.right, 0);
							if (e == i && c.util.getNodeTextLength(o.right) > 0) {
								d.selectNode(o.right);
								e = new c.range(this.doc);
								e.selectTextNode(o.left);
								if (g > 0)
									j -= e.endNode.nodeValue.length;
								e.selectTextNode(o.right);
								i = e.startNode
							}
						}
						if (k) {
							m = d.startNode;
							if (m.nodeType == 1) {
								if (m.nodeName.toLowerCase() == "br")
									return;
								d.selectNode(m)
							} else
								return
						} else if (p) {
							g = this.splitNodeParent(p, i, j);
							d.setEnd(g.left, 0);
							m == p && d.setStart(g.left, 0)
						}
						p = function(s, t) {
							if (t.charAt(0) == ".") {
								var q = c.util.getJsKey(t.substr(1));
								s.style[q] = ""
							} else {
								if (c.browser.IE && c.browser.VERSION < 8
										&& t == "class")
									t = "className";
								s.removeAttribute(t)
							}
						};
						g = d.getNodeList();
						d.setTextStart(d.startNode, d.startPos);
						d.setTextEnd(d.endNode, d.endPos);
						j = 0;
						for (k = g.length; j < k; j++) {
							m = g[j];
							if (m.nodeType == 1) {
								o = m.tagName.toLowerCase();
								if (b[o]) {
									o = b[o];
									e = 0;
									for (i = o.length; e < i; e++)
										if (o[e] == "*") {
											c.util.removeParent(m);
											break
										} else {
											p(m, o[e]);
											var n = [];
											if (m.outerHTML) {
												attrHash = c.util
														.getAttrList(m.outerHTML);
												c.each(attrHash,
														function(s, t) {
															n.push( {
																name : s,
																value : t
															})
														})
											} else
												n = m.attributes;
											if (n.length == 0) {
												c.util.removeParent(m);
												break
											} else if (n[0].name == "style"
													&& n[0].value === "") {
												c.util.removeParent(m);
												break
											}
										}
								}
								if (b["*"]) {
									o = b["*"];
									e = 0;
									for (i = o.length; e < i; e++)
										p(m, o[e])
								}
							}
						}
						try {
							this.keSel.addRange(d)
						} catch (r) {
						}
					}
				}
			}
		};
		c.format = {
			getUrl : function(a, b, d, e) {
				if (!b)
					return a;
				b = b.toLowerCase();
				if (!c.util.inArray(b, [ "absolute", "relative", "domain" ]))
					return a;
				d = d || location.protocol + "//" + location.host;
				if (e === f) {
					var g = location.pathname.match(/^(\/.*)\//);
					e = g ? g[1] : ""
				}
				if (g = a.match(/^(\w+:\/\/[^\/]*)/)) {
					if (g[1] !== d)
						return a
				} else if (a.match(/^\w+:/))
					return a;
				g = function(j) {
					j = j.split("/");
					paths = [];
					for ( var k = 0, l = j.length; k < l; k++) {
						var m = j[k];
						if (m == "..")
							paths.length > 0 && paths.pop();
						else
							m !== "" && m != "." && paths.push(m)
					}
					return "/" + paths.join("/")
				};
				if (a.match(/^\//))
					a = d + g(a.substr(1));
				else
					a.match(/^\w+:\/\//) || (a = d + g(e + "/" + a));
				if (b == "relative") {
					var i = function(j, k) {
						if (a.substr(0, j.length) === j) {
							for ( var l = [], m = 0; m < k; m++)
								l.push("..");
							m = ".";
							if (l.length > 0)
								m += "/" + l.join("/");
							if (e == "/")
								m += "/";
							return m + a.substr(j.length)
						} else if (l = j.match(/^(.*)\//))
							return i(l[1], ++k)
					};
					a = i(d + e, 0).substr(2)
				} else if (b == "absolute")
					if (a.substr(0, d.length) === d)
						a = a.substr(d.length);
				return a
			},
			getHtml : function(a, b, d) {
				var e = b ? true : false;
				a = a.replace(/(<pre[^>]*>)([\s\S]*?)(<\/pre>)/ig, function(o,
						n, r, s) {
					return n + r.replace(/<br[^>]*>/ig, "\n") + s
				});
				var g = {}, i = [ "xx-small", "x-small", "small", "medium",
						"large", "x-large", "xx-large" ];
				e
						&& c
								.each(
										b,
										function(o, n) {
											for ( var r = o.split(","), s = 0, t = r.length; s < t; s++)
												g[r[s]] = c.util.arrayToHash(n)
										});
				var j = false, k = c.util.arrayToHash(c.setting.noEndTags);
				c.util.arrayToHash(c.setting.inlineTags);
				var l = c.util.arrayToHash(c.setting.endlineTags);
				a = a
						.replace(
								/((?:\r\n|\n|\r)*)<(\/)?([\w\-:]+)((?:\s+|(?:\s+[\w\-:]+)|(?:\s+[\w\-:]+=[^\s"'<>]+)|(?:\s+[\w\-:]+="[^"]*")|(?:\s+[\w\-:]+='[^']*'))*)(\/)?>((?:\r\n|\n|\r)*)/g,
								function(o, n, r, s, t, q, w) {
									n = n || "";
									r = r || "";
									var u = s.toLowerCase();
									s = t || "";
									q = q ? " " + q : "";
									w = w || "";
									if (u === "script" && r !== "")
										j = false;
									if (j)
										return o;
									if (u === "script" && r === "")
										j = true;
									if (e && typeof g[u] == "undefined")
										return "";
									if (q === "" && typeof k[u] != "undefined")
										q = " /";
									if (u in l) {
										if (r || q)
											w = "\n"
									} else if (w)
										w = " ";
									if (u !== "script" && u !== "style")
										n = "";
									if (u === "font") {
										var x = {}, A = "";
										s = s
												.replace(
														/\s*([\w\-:]+)=([^\s"'<>]+|"[^"]*"|'[^']*')/g,
														function(B, y, v) {
															y = y.toLowerCase();
															v = v || "";
															v = v
																	.replace(
																			/^["']|["']$/g,
																			"");
															if (y === "color") {
																x.color = v;
																return " "
															}
															if (y === "size") {
																x["font-size"] = i[parseInt(v) - 1]
																		|| "";
																return " "
															}
															if (y === "face") {
																x["font-family"] = v;
																return " "
															}
															if (y === "style") {
																A = v;
																return " "
															}
															return B
														});
										if (A && !/;$/.test(A))
											A += ";";
										c.each(x, function(B, y) {
											if (y !== "") {
												if (/\s/.test(y))
													y = "'" + y + "'";
												A += B + ":" + y + ";"
											}
										});
										if (A)
											s += ' style="' + A + '"';
										u = "span"
									}
									if (s !== "") {
										s = s
												.replace(
														/\s*([\w\-:]+)=([^\s"'<>]+|"[^"]*"|'[^']*')/g,
														function(B, y, v) {
															B = y.toLowerCase();
															v = v || "";
															if (e)
																if (B.charAt(0) === "."
																		|| B !== "style"
																		&& typeof g[u][B] == "undefined")
																	return " ";
															if (v === "")
																v = '""';
															else {
																if (B === "style") {
																	v = v
																			.substr(
																					1,
																					v.length - 2);
																	v = v
																			.replace(
																					/\s*([^\s]+?)\s*:(.*?)(;|$)/g,
																					function(
																							z,
																							D,
																							C) {
																						z = D
																								.toLowerCase();
																						if (e)
																							if (typeof g[u].style == "undefined"
																									&& typeof g[u]["."
																											+ z] == "undefined")
																								return "";
																						C = c.util
																								.trim(C);
																						C = c.util
																								.rgbToHex(C);
																						return z
																								+ ":"
																								+ C
																								+ ";"
																					});
																	v = c.util
																			.trim(v);
																	if (v === "")
																		return "";
																	v = '"' + v + '"'
																}
																if (c.util
																		.inArray(
																				B,
																				[
																						"src",
																						"href" ])) {
																	if (v
																			.charAt(0) === '"')
																		v = v
																				.substr(
																						1,
																						v.length - 2);
																	v = c.format
																			.getUrl(
																					v,
																					d)
																}
																if (v.charAt(0) !== '"')
																	v = '"' + v + '"'
															}
															return " " + B
																	+ "=" + v
																	+ " "
														});
										s = s
												.replace(
														/\s+(checked|selected|disabled|readonly)(\s+|$)/ig,
														function(B, y) {
															var v = y
																	.toLowerCase();
															if (e)
																if (v.charAt(0) === "."
																		|| typeof g[u][v] == "undefined")
																	return " ";
															return " " + v
																	+ '="' + v
																	+ '" '
														});
										s = c.util.trim(s);
										if (s = s.replace(/\s+/g, " "))
											s = " " + s;
										return n + "<" + r + u + s + q + ">"
												+ w
									} else
										return n + "<" + r + u + q + ">" + w
								});
				if (!c.browser.IE) {
					a = a.replace(/<p><br\s+\/>\n<\/p>/ig, "<p>&nbsp;</p>");
					a = a.replace(/<br\s+\/>\n<\/p>/ig, "</p>")
				}
				a = a.replace(/\u200B/g, "");
				var m = c.setting.inlineTags.join("|"), p = function(o) {
					var n = o.replace(new RegExp("<(" + m + ")[^>]*><\\/(" + m
							+ ")>", "ig"), function(r, s, t) {
						return s == t ? "" : r
					});
					if (o !== n)
						n = p(n);
					return n
				};
				return c.util.trim(p(a))
			}
		};
		c.addClass = function(a, b) {
			if (typeof a == "object") {
				var d = a.className;
				if (d) {
					if ((" " + d + " ").indexOf(" " + b + " ") < 0)
						a.className = d + " " + b
				} else
					a.className = b
			} else if (typeof a == "string")
				a = /\s+class\s*=/.test(a) ? a
						.replace(/(\s+class=["']?)([^"']*)(["']?[\s>])/,
								function(e, g, i, j) {
									return (" " + i + " ").indexOf(" " + b
											+ " ") < 0 ? i === "" ? g + b + j
											: g + i + " " + b + j : e
								}) : a.substr(0, a.length - 1) + ' class="' + b
						+ '">';
			return a
		};
		c.removeClass = function(a, b) {
			var d = a.className || "";
			d = " " + d + " ";
			b = " " + b + " ";
			if (d.indexOf(b) >= 0) {
				d = c.util.trim(d.replace(new RegExp(b, "ig"), ""));
				if (d === "") {
					d = a.getAttribute("class") ? "class" : "className";
					a.removeAttribute(d)
				} else
					a.className = d
			}
			return a
		};
		c.getComputedStyle = function(a, b) {
			var d = a.ownerDocument, e = d.parentWindow || d.defaultView;
			d = c.util.getJsKey(b);
			var g = "";
			if (e.getComputedStyle) {
				e = e.getComputedStyle(a, null);
				g = e[d] || e.getPropertyValue(b) || a.style[d]
			} else if (a.currentStyle)
				g = a.currentStyle[d] || a.style[d];
			return g
		};
		c.getCommonAncestor = function(a, b) {
			function d(j) {
				for (; j;) {
					if (j.nodeType == 1)
						if (j.tagName.toLowerCase() === b)
							return j;
					j = j.parentNode
				}
				return null
			}
			var e = a.range, g = a.keRange, i = g.startNode;
			g = g.endNode;
			if (c.util.inArray(b, [ "table", "td", "tr" ]))
				if (c.browser.IE)
					if (e.item) {
						if (e.item(0).nodeName.toLowerCase() === b)
							i = g = e.item(0)
					} else {
						i = e.duplicate();
						i.collapse(true);
						e = e.duplicate();
						e.collapse(false);
						i = i.parentElement();
						g = e.parentElement()
					}
				else {
					i = e.cloneRange();
					i.collapse(true);
					e = e.cloneRange();
					e.collapse(false);
					i = i.startContainer;
					g = e.startContainer
				}
			e = d(i);
			i = d(g);
			if (e && i && e === i)
				return e;
			return null
		};
		c.queryCommandValue = function(a, b) {
			function d() {
				var j = a.queryCommandValue(b);
				if (typeof j !== "string")
					j = "";
				return j
			}
			b = b.toLowerCase();
			var e = "";
			if (b === "fontname") {
				e = d();
				e = e.replace(/['"]/g, "")
			} else if (b === "formatblock") {
				e = d();
				if (e === "") {
					var g = new c.selection(a), i = c
							.getCommonAncestor(g, "h1");
					i || (i = c.getCommonAncestor(g, "h2"));
					i || (i = c.getCommonAncestor(g, "h3"));
					i || (i = c.getCommonAncestor(g, "h4"));
					i || (i = c.getCommonAncestor(g, "p"));
					if (i)
						e = i.nodeName
				}
				if (e === "Normal")
					e = "p"
			} else if (b === "fontsize") {
				g = new c.selection(a);
				if (i = c.getCommonAncestor(g, "span"))
					e = c.getComputedStyle(i, "font-size")
			} else if (b === "textcolor") {
				g = new c.selection(a);
				if (i = c.getCommonAncestor(g, "span"))
					e = c.getComputedStyle(i, "color");
				e = c.util.rgbToHex(e);
				if (e === "")
					e = "default"
			} else if (b === "bgcolor") {
				g = new c.selection(a);
				if (i = c.getCommonAncestor(g, "span"))
					e = c.getComputedStyle(i, "background-color");
				e = c.util.rgbToHex(e);
				if (e === "")
					e = "default"
			}
			return e.toLowerCase()
		};
		c.util = {
			getDocumentElement : function(a) {
				a = a || document;
				return a.compatMode != "CSS1Compat" ? a.body
						: a.documentElement
			},
			getDocumentHeight : function(a) {
				a = this.getDocumentElement(a);
				return Math.max(a.scrollHeight, a.clientHeight)
			},
			getDocumentWidth : function(a) {
				a = this.getDocumentElement(a);
				return Math.max(a.scrollWidth, a.clientWidth)
			},
			createTable : function(a) {
				a = c.$$("table", a);
				a.cellPadding = 0;
				a.cellSpacing = 0;
				a.border = 0;
				return {
					table : a,
					cell : a.insertRow(0).insertCell(0)
				}
			},
			loadStyle : function(a) {
				var b = c.$$("link");
				b.setAttribute("type", "text/css");
				b.setAttribute("rel", "stylesheet");
				b.setAttribute("href", a);
				document.getElementsByTagName("head")[0].appendChild(b)
			},
			getAttrList : function(a) {
				for ( var b = /\s+(?:([\w\-:]+)|(?:([\w\-:]+)=([\w\-:]+))|(?:([\w\-:]+)="([^"]*)")|(?:([\w\-:]+)='([^']*)'))(?=(?:\s|\/|>)+)/g, d, e, g = {}; d = b
						.exec(a);) {
					e = d[1] || d[2] || d[4] || d[6];
					d = d[1] || (d[2] ? d[3] : d[4] ? d[5] : d[7]);
					g[e] = d
				}
				return g
			},
			inArray : function(a, b) {
				for ( var d = 0; d < b.length; d++)
					if (a == b[d])
						return true;
				return false
			},
			trim : function(a) {
				return a.replace(/^\s+|\s+$/g, "")
			},
			getJsKey : function(a) {
				var b = a.split("-");
				a = "";
				for ( var d = 0, e = b.length; d < e; d++)
					a += d > 0 ? b[d].charAt(0).toUpperCase() + b[d].substr(1)
							: b[d];
				return a
			},
			arrayToHash : function(a) {
				for ( var b = {}, d = 0, e = a.length; d < e; d++)
					b[a[d]] = 1;
				return b
			},
			escape : function(a) {
				a = a.replace(/&/g, "&amp;");
				a = a.replace(/</g, "&lt;");
				a = a.replace(/>/g, "&gt;");
				return a = a.replace(/"/g, "&quot;")
			},
			unescape : function(a) {
				a = a.replace(/&lt;/g, "<");
				a = a.replace(/&gt;/g, ">");
				a = a.replace(/&quot;/g, '"');
				return a = a.replace(/&amp;/g, "&")
			},
			getScrollPos : function() {
				var a, b;
				if (c.browser.IE || c.browser.OPERA) {
					b = this.getDocumentElement();
					a = b.scrollLeft;
					b = b.scrollTop
				} else {
					a = window.scrollX;
					b = window.scrollY
				}
				return {
					x : a,
					y : b
				}
			},
			getElementPos : function(a) {
				var b = 0, d = 0;
				if (a.getBoundingClientRect) {
					d = a.getBoundingClientRect();
					a = this.getScrollPos();
					b = d.left + a.x;
					d = d.top + a.y
				} else {
					b = a.offsetLeft;
					d = a.offsetTop;
					for (a = a.offsetParent; a;) {
						b += a.offsetLeft;
						d += a.offsetTop;
						a = a.offsetParent
					}
				}
				return {
					x : b,
					y : d
				}
			},
			getCoords : function(a) {
				a = a || window.event;
				return {
					x : a.clientX,
					y : a.clientY
				}
			},
			setOpacity : function(a, b) {
				if (typeof a.style.opacity == "undefined")
					a.style.filter = b == 100 ? "" : "alpha(opacity=" + b + ")";
				else
					a.style.opacity = b == 100 ? "" : b / 100
			},
			getIframeDoc : function(a) {
				return a.contentDocument || a.contentWindow.document
			},
			rgbToHex : function(a) {
				function b(d) {
					d = parseInt(d).toString(16);
					return d.length > 1 ? d : "0" + d
				}
				return a
						.replace(
								/rgb\s*?\(\s*?(\d+)\s*?,\s*?(\d+)\s*?,\s*?(\d+)\s*?\)/ig,
								function(d, e, g, i) {
									return "#" + b(e) + b(g) + b(i)
								})
			},
			parseJson : function(a) {
				var b;
				if (b = /\{[\s\S]*\}|\[[\s\S]*\]/.exec(a))
					a = b[0];
				b = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g;
				b.lastIndex = 0;
				if (b.test(a))
					a = a.replace(b, function(d) {
						return "\\u"
								+ ("0000" + d.charCodeAt(0).toString(16))
										.slice(-4)
					});
				if (/^[\],:{}\s]*$/
						.test(a
								.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g,
										"@")
								.replace(
										/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g,
										"]")
								.replace(/(?:^|:|,)(?:\s*\[)+/g, "")))
					return eval("(" + a + ")");
				throw "JSON parse error";
			},
			getParam : function(a, b) {
				return a.match(new RegExp("[?&]" + b + "=([^?&]+)", "i")) ? unescape(RegExp.$1)
						: ""
			},
			createRange : function(a) {
				return a.body.createTextRange ? a.body.createTextRange() : a
						.createRange()
			},
			getNodeType : function(a) {
				return a.nodeType == 1
						&& c.util.inArray(a.tagName.toLowerCase(),
								c.setting.noEndTags) ? 88 : a.nodeType
			},
			inMarquee : function(a) {
				a = a;
				for ( var b; a;) {
					b = a.nodeName.toLowerCase();
					if (b == "marquee" || b == "select")
						return true;
					a = a.parentNode
				}
				return false
			},
			moveToElementText : function(a, b) {
				this.inMarquee(b) || a.moveToElementText(b)
			},
			getNodeTextLength : function(a) {
				var b = c.util.getNodeType(a);
				if (b == 1)
					return a.innerHTML.replace(/<.*?>/ig, "").length;
				else if (b == 3)
					return a.nodeValue.length
			},
			getNodeStartRange : function(a, b) {
				var d = c.util.createRange(a), e = b.nodeType;
				if (e == 1) {
					c.util.moveToElementText(d, b);
					return d
				} else if (e == 3) {
					e = 0;
					for ( var g = b.previousSibling; g;) {
						if (g.nodeType == 1) {
							var i = c.util.createRange(a);
							c.util.moveToElementText(i, g);
							d.setEndPoint("StartToEnd", i);
							d.moveStart("character", e);
							return d
						} else if (g.nodeType == 3)
							e += g.nodeValue.length;
						g = g.previousSibling
					}
					c.util.moveToElementText(d, b.parentNode);
					d.moveStart("character", e);
					return d
				}
			},
			removeParent : function(a) {
				if (a.hasChildNodes)
					for ( var b = a.firstChild; b;) {
						var d = b.nextSibling;
						a.parentNode.insertBefore(b, a);
						b = d
					}
				a.parentNode.removeChild(a)
			},
			pluginLang : function(a, b) {
				c.each(c.lang.plugins[a], function(d, e) {
					var g = c.$("lang." + d, b);
					if (g) {
						g.parentNode.insertBefore(b.createTextNode(e), g);
						g.parentNode.removeChild(g)
					}
				})
			},
			drag : function(a, b, d, e) {
				var g = c.g[a];
				b.onmousedown = function(i) {
					function j(y) {
						if (x) {
							var v = c.util.getCoords(y), z = c.util
									.getScrollPos();
							y = parseInt(v.y - t - w + z.y);
							v = parseInt(v.x - q - u + z.x);
							e(o, n, r, s, y, v)
						}
					}
					function k(y) {
						if (x) {
							var v = c.util.getCoords(y, g.iframeDoc);
							y = parseInt(A.y + v.y - t - w);
							v = parseInt(A.x + v.x - q - u);
							e(o, n, r, s, y, v)
						}
					}
					function l(y) {
						x = false;
						m.releaseCapture && m.releaseCapture();
						c.event.remove(document, "mousemove", j);
						c.event.remove(document, "mouseup", l);
						c.event.remove(g.iframeDoc, "mousemove", k);
						c.event.remove(g.iframeDoc, "mouseup", l);
						c.event.remove(document, "selectstart", B);
						c.event.stop(y);
						return false
					}
					var m = this;
					i = i || window.event;
					var p = c.util.getCoords(i), o = parseInt(d.style.top), n = parseInt(d.style.left), r = d.style.width, s = d.style.height;
					if (r.match(/%$/))
						r = d.offsetWidth + "px";
					if (s.match(/%$/))
						s = d.offsetHeight + "px";
					r = parseInt(r);
					s = parseInt(s);
					var t = p.y, q = p.x;
					p = c.util.getScrollPos();
					var w = p.y, u = p.x, x = true, A = c.util
							.getElementPos(g.iframe), B = function() {
						return false
					};
					c.event.add(document, "mousemove", j);
					c.event.add(document, "mouseup", l);
					c.event.add(g.iframeDoc, "mousemove", k);
					c.event.add(g.iframeDoc, "mouseup", l);
					c.event.add(document, "selectstart", B);
					m.setCapture && m.setCapture();
					c.event.stop(i);
					return false
				}
			},
			resize : function(a, b, d, e, g) {
				g = typeof g == "undefined" ? true : g;
				a = c.g[a];
				if (a.container)
					if (!(e && (parseInt(b) <= a.minWidth || parseInt(d) <= a.minHeight))) {
						if (g)
							a.container.style.width = b;
						a.container.style.height = d;
						b = parseInt(d) - a.toolbarHeight - a.statusbarHeight;
						if (b >= 0) {
							a.iframe.style.height = b + "px";
							a.newTextarea.style.height = ((c.browser.IE
									&& c.browser.VERSION < 8 || document.compatMode != "CSS1Compat")
									&& b >= 2 ? b - 2 : b)
									+ "px"
						}
					}
			},
			hideLoadingPage : function(a) {
				a = c.g[a].dialogStack;
				a = a[a.length - 1];
				a.loading.style.display = "none";
				a.iframe.style.display = ""
			},
			showLoadingPage : function(a) {
				a = c.g[a].dialogStack;
				a = a[a.length - 1];
				a.loading.style.display = "";
				a.iframe.style.display = "none"
			},
			setDefaultPlugin : function() {
				for ( var a = [ "selectall", "justifyleft", "justifycenter",
						"justifyright", "justifyfull", "insertorderedlist",
						"insertunorderedlist", "indent", "outdent",
						"subscript", "superscript", "bold", "italic",
						"underline", "strikethrough" ], b = {
					bold : "B",
					italic : "I",
					underline : "U"
				}, d = 0; d < a.length; d++) {
					var e = a[d], g = {};
					if (e in b)
						g.init = function(i) {
							return function(j) {
								c.event.ctrl(c.g[j].iframeDoc, b[i],
										function() {
											c.plugin[i].click(j);
											c.util.focus(j)
										}, j)
							}
						}(e);
					g.click = function(i) {
						return function(j) {
							c.util.execCommand(j, i, null)
						}
					}(e);
					c.plugin[e] = g
				}
			},
			getFullHtml : function(a) {
				var b = "<html>";
				b += "<head>";
				b += '<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />';
				b += "<title>KindEditor</title>";
				b += '<link href="' + c.g[a].skinsPath
						+ "common/editor.css?ver=" + escape(c.version)
						+ '" rel="stylesheet" type="text/css" />';
				a = c.g[a].cssPath;
				if (typeof a == "string")
					a = [ a ];
				for ( var d = 0, e = a.length; d < e; d++)
					if (a[d] !== "")
						b += '<link href="' + a[d] + '" rel="stylesheet" type="text/css" />';
				b += "</head>";
				b += '<body class="ke-content"></body>';
				b += "</html>";
				return b
			},
			getMediaType : function(a) {
				return a.match(/\.(rm|rmvb)(\?|$)/i) ? "rm" : a
						.match(/\.(swf|flv)(\?|$)/i) ? "flash" : "media"
			},
			getMediaImage : function(a, b, d) {
				var e = d.width, g = d.height;
				b = b || this.getMediaType(d.src);
				d = this.getMediaEmbed(d);
				var i = "";
				if (e > 0)
					i += "width:" + e + "px;";
				if (g > 0)
					i += "height:" + g + "px;";
				a = '<img class="' + ("ke-" + b) + '" src="' + c.g[a].skinsPath
						+ 'common/blank.gif" ';
				if (i !== "")
					a += 'style="' + i + '" ';
				a += 'kesrctag="' + escape(d) + '" alt="" />';
				return a
			},
			getMediaEmbed : function(a) {
				var b = "<embed ";
				c.each(a, function(d, e) {
					b += d + '="' + e + '" '
				});
				b += "/>";
				return b
			},
			execGetHtmlHooks : function(a, b) {
				for ( var d = c.g[a].getHtmlHooks, e = 0, g = d.length; e < g; e++)
					b = d[e](b);
				return b
			},
			execSetHtmlHooks : function(a, b) {
				for ( var d = c.g[a].setHtmlHooks, e = 0, g = d.length; e < g; e++)
					b = d[e](b);
				return b
			},
			execOnchangeHandler : function(a) {
				a = c.g[a].onchangeHandlerStack;
				for ( var b = 0, d = a.length; b < d; b++)
					a[b]()
			},
			toData : function(a, b) {
				var d = c.g[a], e = this.execGetHtmlHooks(a, b);
				e = e.replace(/^\s*<br[^>]*>\s*$/ig, "");
				e = e.replace(/^\s*<p>\s*&nbsp;\s*<\/p>\s*$/ig, "");
				return d.filterMode ? c.format
						.getHtml(e, d.htmlTags, d.urlType) : c.format.getHtml(
						e, null, d.urlType)
			},
			getData : function(a, b) {
				var d = c.g[a];
				(b = b === f ? d.wyswygMode : b)
						|| this.innerHtml(d.iframeDoc.body, c.util
								.execSetHtmlHooks(a, d.newTextarea.value));
				return this.toData(a, d.iframeDoc.body.innerHTML)
			},
			getSrcData : function(a) {
				var b = c.g[a];
				b.wyswygMode
						|| this.innerHtml(b.iframeDoc.body, c.util
								.execSetHtmlHooks(a, b.newTextarea.value));
				return b.iframeDoc.body.innerHTML
			},
			getPureData : function(a) {
				return this.extractText(this.getData(a))
			},
			extractText : function(a) {
				a = a.replace(/<(?!img|embed).*?>/ig, "");
				return a = a.replace(/&nbsp;/ig, " ")
			},
			isEmpty : function(a) {
				return this.getPureData(a).replace(/\r\n|\n|\r/, "").replace(
						/^\s+|\s+$/, "") === ""
			},
			setData : function(a) {
				var b = c.g[a];
				if (b.srcTextarea)
					b.srcTextarea.value = this.getData(a)
			},
			focus : function(a) {
				a = c.g[a];
				a.wyswygMode ? a.iframeWin.focus() : a.newTextarea.focus()
			},
			click : function(a, b) {
				this.focus(a);
				c.hideMenu(a);
				c.plugin[b].click(a)
			},
			selection : function(a) {
				if (!c.browser.IE || !c.g[a].keRange)
					this.setSelection(a)
			},
			setSelection : function(a) {
				a = c.g[a];
				var b = new c.selection(a.iframeDoc);
				if (!c.browser.IE
						|| b.range.item
						|| b.range.parentElement().ownerDocument === a.iframeDoc) {
					a.keSel = b;
					a.keRange = a.keSel.keRange;
					a.sel = a.keSel.sel;
					a.range = a.keSel.range
				}
			},
			select : function(a) {
				c.browser.IE && c.g[a].wyswygMode && c.g[a].range
						&& c.g[a].range.select()
			},
			execCommand : function(a, b, d) {
				c.util.focus(a);
				c.util.select(a);
				try {
					c.g[a].iframeDoc.execCommand(b, false, d)
				} catch (e) {
				}
				c.toolbar.updateState(a);
				c.util.execOnchangeHandler(a)
			},
			innerHtml : function(a, b) {
				if (c.browser.IE) {
					a.innerHTML = '<img id="__ke_temp_tag__" width="0" height="0" />' + b;
					var d = c.$("__ke_temp_tag__", a.ownerDocument);
					d && d.parentNode.removeChild(d)
				} else
					a.innerHTML = b
			},
			pasteHtml : function(a, b, d) {
				a = c.g[a];
				if (d)
					b = '<img id="__ke_temp_tag__" width="0" height="0" />' + b;
				else
					b += '<img id="__ke_temp_tag__" width="0" height="0" />';
				if (c.browser.IE)
					if (a.range.item)
						a.range.item(0).outerHTML = b;
					else
						a.range.pasteHTML("\u200b" + b);
				else {
					a.range.deleteContents();
					b = a.range.createContextualFragment(b);
					a.range.insertNode(b)
				}
				b = c.$("__ke_temp_tag__", a.iframeDoc);
				d = a.iframeDoc.createTextNode("");
				b.parentNode.replaceChild(d, b);
				a.keRange.selectNode(d);
				a.keSel.addRange(a.keRange)
			},
			insertHtml : function(a, b) {
				if (b !== "") {
					var d = c.g[a];
					if (d.wyswygMode)
						if (d.range) {
							b = this.execSetHtmlHooks(a, b);
							if (c.browser.IE) {
								this.select(a);
								if (d.range.item)
									try {
										d.range.item(0).outerHTML = b
									} catch (e) {
										d = d.range.item(0);
										var g = d.parentNode;
										g.removeChild(d);
										if (g.nodeName.toLowerCase() != "body")
											g = g.parentNode;
										this.innerHtml(g, b + g.innerHTML)
									}
								else
									d.range.pasteHTML("\u200b" + b)
							} else if (c.browser.GECKO && c.browser.VERSION < 3) {
								this.execCommand(a, "inserthtml", b);
								return
							} else
								this.pasteHtml(a, b);
							c.util.execOnchangeHandler(a)
						}
				}
			},
			setFullHtml : function(a, b) {
				var d = c.g[a];
				if (!c.browser.IE && b === "")
					b = "<br />";
				b = c.util.execSetHtmlHooks(a, b);
				this.innerHtml(d.iframeDoc.body, b);
				if (!d.wyswygMode)
					d.newTextarea.value = c.util.getData(a, true);
				c.util.execOnchangeHandler(a)
			},
			selectImageWebkit : function(a, b, d) {
				if (c.browser.WEBKIT) {
					b = b.srcElement || b.target;
					if (b.tagName.toLowerCase() == "img") {
						d && c.util.selection(a);
						d = c.g[a].keRange;
						d.selectNode(b);
						c.g[a].keSel.addRange(d)
					}
				}
			},
			addTabEvent : function(a) {
				var b = c.g[a];
				c.event.add(b.iframeDoc, "keydown", function(d) {
					if (d.keyCode == 9) {
						b.afterTab && b.afterTab(a);
						c.event.stop(d);
						return false
					}
				}, a)
			},
			addContextmenuEvent : function(a) {
				var b = c.g[a];
				b.contextmenuItems.length != 0
						&& b.useContextmenu
						&& c.event
								.add(
										b.iframeDoc,
										"contextmenu",
										function(d) {
											c.hideMenu(a);
											c.util.setSelection(a);
											c.util.selectImageWebkit(a, d,
													false);
											for ( var e = 0, g = [], i = 0, j = b.contextmenuItems.length; i < j; i++) {
												var k = b.contextmenuItems[i];
												if (k === "-")
													g.push(k);
												else if (k.cond && k.cond(a)) {
													g.push(k);
													if (k.options) {
														var l = parseInt(k.options.width) || 0;
														if (l > e)
															e = l
													}
												}
												k = k
											}
											for (; g.length > 0 && g[0] === "-";)
												g.shift();
											for (; g.length > 0
													&& g[g.length - 1] === "-";)
												g.pop();
											k = null;
											i = 0;
											for (j = g.length; i < j; i++) {
												g[i] === "-" && k === "-"
														&& delete g[i];
												k = g[i] || null
											}
											if (g.length > 0) {
												var m = new c.menu( {
													id : a,
													event : d,
													type : "contextmenu",
													width : e
												});
												i = 0;
												for (j = g.length; i < j; i++)
													if (k = g[i])
														if (k === "-")
															i < j - 1
																	&& m
																			.addSeparator();
														else
															m
																	.add(
																			k.text,
																			function(
																					p) {
																				return function() {
																					p
																							.click(
																									a,
																									m)
																				}
																			}
																					(k),
																			k.options);
												m.show();
												c.event.stop(d);
												return false
											}
											return true
										}, a)
			},
			addNewlineEvent : function(a) {
				var b = c.g[a];
				c.browser.IE
						&& b.newlineTag.toLowerCase() != "br"
						|| c.browser.GECKO
						&& c.browser.VERSION < 3
						&& b.newlineTag.toLowerCase() != "p"
						|| c.browser.OPERA
						|| c.event
								.add(
										b.iframeDoc,
										"keydown",
										function(d) {
											if (d.keyCode != 13 || d.shiftKey
													|| d.ctrlKey || d.altKey)
												return true;
											c.util.setSelection(a);
											var e = b.keRange
													.getParentElement();
											if (!c.util.inMarquee(e)) {
												e = e.tagName.toLowerCase();
												if (b.newlineTag.toLowerCase() == "br") {
													if (!c.util.inArray(e, [
															"h1", "h2", "h3",
															"h4", "h5", "h6",
															"li" ])) {
														c.util.pasteHtml(a,
																"<br />");
														e = b.keRange.startNode.nextSibling;
														if (c.browser.IE)
															e
																	|| c.util
																			.pasteHtml(
																					a,
																					"<br />",
																					true);
														else if (c.browser.WEBKIT)
															if (e) {
																var g = new c.range(
																		b.iframeDoc);
																g
																		.selectNode(e.parentNode);
																g
																		.setStart(
																				e,
																				0);
																g
																		.cloneContents().innerHTML
																		.replace(
																				/<(?!img|embed).*?>/ig,
																				"") === ""
																		&& c.util
																				.pasteHtml(
																						a,
																						"<br />",
																						true)
															} else
																c.util
																		.pasteHtml(
																				a,
																				"<br />",
																				true);
														c.event.stop(d);
														return false
													}
												} else
													c.util
															.inArray(
																	e,
																	[
																			"p",
																			"h1",
																			"h2",
																			"h3",
																			"h4",
																			"h5",
																			"h6",
																			"pre",
																			"div",
																			"li" ])
															|| c.util
																	.execCommand(
																			a,
																			"formatblock",
																			"<P>");
												return true
											}
										}, a)
			}
		};
		c.layout = {
			hide : function(a) {
				var b = c.g[a];
				c.hideMenu(a);
				for (a = b.dialogStack; a.length > 0;)
					a[a.length - 1].hide();
				b.maskDiv.style.display = "none"
			}
		};
		c.hideMenu = function(a) {
			a = c.g[a];
			a.hideDiv.innerHTML = "";
			a.hideDiv.style.display = "none"
		};
		c.colorpicker = function(a) {
			var b, d = a.x || 0, e = a.y || 0, g = a.z || 0, i = a.colors
					|| c.setting.colorTable, j = a.doc || document, k = a.onclick, l = (a.selectedColor || "")
					.toLowerCase();
			b = c.$$("div");
			b.className = "ke-colorpicker";
			b.style.top = e + "px";
			b.style.left = d + "px";
			b.style.zIndex = g;
			this.remove = function() {
				j.body.removeChild(b)
			};
			this.getElement = function() {
				function m(q, w, u) {
					if (l === w.toLowerCase())
						u += " ke-colorpicker-cell-selected";
					q.className = u;
					q.title = w || c.lang.noColor;
					q.onmouseover = function() {
						this.className = u + " ke-colorpicker-cell-on"
					};
					q.onmouseout = function() {
						this.className = u
					};
					q.onclick = function() {
						k(w)
					};
					if (w) {
						var x = c.$$("div");
						x.className = "ke-colorpicker-cell-color";
						x.style.backgroundColor = w;
						q.appendChild(x)
					} else
						q.innerHTML = c.lang.noColor
				}
				var p = c.$$("table");
				p.className = "ke-colorpicker-table";
				p.cellPadding = 0;
				p.cellSpacing = 0;
				p.border = 0;
				var o = p.insertRow(0), n = o.insertCell(0);
				n.colSpan = i[0].length;
				m(n, "", "ke-colorpicker-cell-top");
				for ( var r = 0; r < i.length; r++) {
					o = p.insertRow(r + 1);
					for ( var s = 0; s < i[r].length; s++) {
						var t = i[r][s];
						n = o.insertCell(s);
						m(n, t, "ke-colorpicker-cell")
					}
				}
				return p
			};
			this.create = function() {
				b.appendChild(this.getElement());
				c.event.bind(b, "click", function() {
				});
				c.event.bind(b, "mousedown", function() {
				});
				j.body.appendChild(b)
			}
		};
		c.menu = function(a) {
			function b(d, e) {
				var g = a.id, i = 0, j = 0;
				if (this.type == "menu") {
					g = c.g[g].toolbarIcon[a.cmd];
					j = c.util.getElementPos(g[0]);
					i = j.x;
					j = j.y + g[0].offsetHeight
				} else {
					j = c.util.getCoords(a.event);
					g = c.util.getElementPos(c.g[g].iframe);
					i = j.x + g.x;
					j = j.y + g.y + 5
				}
				if (d > 0 || e > 0) {
					g = c.util.getScrollPos();
					var k = c.util.getDocumentElement();
					g = g.x + k.clientWidth - d - 2;
					if (i > g)
						i = g
				}
				return {
					x : i,
					y : j
				}
			}
			(function() {
				var d = a.width;
				this.type = a.type && a.type == "contextmenu" ? a.type : "menu";
				var e = c.$$("div");
				e.className = "ke-" + this.type;
				e.setAttribute("name", a.cmd);
				var g = b.call(this, 0, 0);
				e.style.top = g.y + "px";
				e.style.left = g.x + "px";
				if (a.width)
					e.style.width = /^\d+$/.test(d) ? d + "px" : d;
				c.event.bind(e, "click", function() {
				}, a.id);
				c.event.bind(e, "mousedown", function() {
				}, a.id);
				this.div = e
			}).call(this);
			this.add = function(d, e, g) {
				var i, j, k = false;
				if (g !== f) {
					i = g.height;
					j = g.iconHtml;
					k = g.checked
				}
				var l = this;
				g = c.$$("div");
				g.className = "ke-" + l.type + "-item";
				if (i)
					g.style.height = i;
				var m = c.$$("div");
				m.className = "ke-" + this.type + "-left";
				var p = c.$$("div");
				p.className = "ke-" + l.type + "-center";
				if (i)
					p.style.height = i;
				var o = c.$$("div");
				o.className = "ke-" + this.type + "-right";
				if (i)
					o.style.lineHeight = i;
				g.onmouseover = function() {
					this.className = "ke-" + l.type + "-item ke-" + l.type
							+ "-item-on";
					p.className = "ke-" + l.type + "-center ke-" + l.type
							+ "-center-on"
				};
				g.onmouseout = function() {
					this.className = "ke-" + l.type + "-item";
					p.className = "ke-" + l.type + "-center"
				};
				g.onclick = e;
				g.appendChild(m);
				g.appendChild(p);
				g.appendChild(o);
				if (k)
					c.util
							.innerHtml(m,
									'<span class="ke-common-icon ke-common-icon-url ke-icon-checked"></span>');
				else
					j && c.util.innerHtml(m, j);
				c.util.innerHtml(o, d);
				this.append(g)
			};
			this.addSeparator = function() {
				var d = c.$$("div");
				d.className = "ke-" + this.type + "-separator";
				this.append(d)
			};
			this.append = function(d) {
				this.div.appendChild(d)
			};
			this.insert = function(d) {
				c.util.innerHtml(this.div, d)
			};
			this.hide = function() {
				c.hideMenu(a.id)
			};
			this.show = function() {
				this.hide();
				var d = a.id;
				c.g[d].hideDiv.style.display = "";
				c.g[d].hideDiv.appendChild(this.div);
				d = b.call(this, this.div.clientWidth, this.div.clientHeight);
				this.div.style.top = d.y + "px";
				this.div.style.left = d.x + "px"
			};
			this.picker = function(d) {
				this.append((new c.colorpicker( {
					colors : c.g[a.id].colorTable,
					onclick : function(e) {
						c.plugin[a.cmd].exec(a.id, e)
					},
					selectedColor : d
				})).getElement());
				this.show()
			}
		};
		c.dialog = function(a) {
			function b() {
				c.util.getDocumentElement();
				var i = c.util.getScrollPos();
				e = i.y;
				g = i.x
			}
			function d() {
				var i = this.width + this.widthMargin, j = this.height
						+ this.heightMargin, k = a.id, l = c.g[k], m = 0, p = 0;
				if (l.dialogAlignType == "page") {
					l = c.util.getDocumentElement();
					k = c.util.getScrollPos();
					m = Math.round(k.x + (l.clientWidth - i) / 2);
					p = Math.round(k.y + (l.clientHeight - j) / 2)
				} else {
					k = c.util.getElementPos(c.g[k].container);
					l = l.container;
					m = Math.round(l.clientWidth / 2) - Math.round(i / 2);
					j = Math.round(l.clientHeight / 2) - Math.round(j / 2);
					m = m < 0 ? k.x : k.x + m;
					p = j < 0 ? k.y : k.y + j
				}
				return {
					x : m < 0 ? 0 : m,
					y : p < 0 ? 0 : p
				}
			}
			this.widthMargin = 30;
			this.heightMargin = 100;
			this.zIndex = 19811214;
			this.width = a.width;
			this.height = a.height;
			var e, g;
			this.beforeHide = a.beforeHide;
			this.afterHide = a.afterHide;
			this.beforeShow = a.beforeShow;
			this.afterShow = a.afterShow;
			this.ondrag = a.ondrag;
			this.resize = function(i, j) {
				if (i)
					this.width = i;
				if (j)
					this.height = j;
				this.hide();
				this.show()
			};
			this.hide = function() {
				this.beforeHide && this.beforeHide(i);
				var i = a.id, j = c.g[i].dialogStack;
				if (j[j.length - 1] == this) {
					var k = j.pop().iframe;
					k.src = "javascript:false";
					k.parentNode.removeChild(k);
					document.body.removeChild(this.div);
					if (j.length < 1)
						c.g[i].maskDiv.style.display = "none";
					c.event.remove(window, "resize", b);
					c.event.remove(window, "scroll", b);
					this.afterHide && this.afterHide(i);
					c.util.focus(i)
				}
			};
			this.show = function() {
				this.beforeShow && this.beforeShow(j);
				var i = this, j = a.id, k = c.$$("div");
				k.className = "ke-dialog";
				c.event.bind(k, "click", function() {
				}, j);
				var l = c.g[j].dialogStack;
				if (l.length > 0)
					this.zIndex = l[l.length - 1].zIndex + 1;
				k.style.zIndex = this.zIndex;
				l = d.call(this);
				k.style.top = l.y + "px";
				k.style.left = l.x + "px";
				var m;
				if (c.g[j].shadowMode) {
					l = c.$$("table");
					l.className = "ke-dialog-table";
					l.cellPadding = 0;
					l.cellSpacing = 0;
					l.border = 0;
					for ( var p = [ "t", "m", "b" ], o = [ "l", "c", "r" ], n = 0; n < 3; n++)
						for ( var r = l.insertRow(n), s = 0; s < 3; s++) {
							var t = r.insertCell(s);
							t.className = "ke-" + p[n] + o[s];
							if (n == 1 && s == 1)
								m = t;
							else
								t.innerHTML = '<span class="ke-dialog-empty"></span>'
						}
					k.appendChild(l)
				} else {
					c.addClass(k, "ke-dialog-no-shadow");
					m = k
				}
				l = c.$$("div");
				l.className = "ke-dialog-title";
				l.innerHTML = a.title;
				p = c.$$("span");
				p.className = "ke-dialog-close";
				c.g[j].shadowMode ? c.addClass(p, "ke-dialog-close-shadow") : c
						.addClass(p, "ke-dialog-close-no-shadow");
				p.alt = c.lang.close;
				p.title = c.lang.close;
				p.onclick = function() {
					i.hide();
					c.util.select(j)
				};
				l.appendChild(p);
				b();
				c.event.add(window, "resize", b);
				c.event.add(window, "scroll", b);
				c.util.drag(j, l, k, function(q, w, u, x, A, B) {
					i.ondrag && i.ondrag(j);
					b();
					A = q + A;
					B = w + B;
					if (A < e)
						A = e;
					if (B < g)
						B = g;
					k.style.top = A + "px";
					k.style.left = B + "px"
				});
				m.appendChild(l);
				o = c.$$("div");
				o.className = "ke-dialog-body";
				l = c.util.createTable();
				l.table.className = "ke-loading-table";
				l.table.style.width = this.width + "px";
				l.table.style.height = this.height + "px";
				p = c.$$("span");
				p.className = "ke-loading-img";
				l.cell.appendChild(p);
				p = c.g[j].dialogStack.length == 0 && c.g[j].dialog ? c.g[j].dialog
						: c.$$("iframe");
				p.className = a.useFrameCSS ? "ke-dialog-iframe ke-dialog-iframe-border"
						: "ke-dialog-iframe";
				p.setAttribute("frameBorder", "0");
				p.style.width = this.width + "px";
				p.style.height = this.height + "px";
				p.style.display = "none";
				o.appendChild(p);
				o.appendChild(l.table);
				m.appendChild(o);
				s = c.$$("div");
				s.className = "ke-dialog-bottom";
				r = n = o = null;
				if (a.previewButton) {
					r = c.$$("input");
					r.className = "ke-button ke-dialog-preview";
					r.type = "button";
					r.name = "previewButton";
					r.value = a.previewButton;
					r.onclick = function() {
						var q = c.g[j].dialogStack;
						q[q.length - 1] == i && c.plugin[a.cmd].preview(j)
					};
					s.appendChild(r)
				}
				if (a.yesButton) {
					n = c.$$("input");
					n.className = "ke-button ke-dialog-yes";
					n.type = "button";
					n.name = "yesButton";
					n.value = a.yesButton;
					n.onclick = function() {
						var q = c.g[j].dialogStack;
						q[q.length - 1] == i && c.plugin[a.cmd].exec(j)
					};
					s.appendChild(n)
				}
				if (a.noButton) {
					o = c.$$("input");
					o.className = "ke-button ke-dialog-no";
					o.type = "button";
					o.name = "noButton";
					o.value = a.noButton;
					o.onclick = function() {
						i.hide();
						c.util.select(j)
					};
					s.appendChild(o)
				}
				if (a.yesButton || a.noButton || a.previewButton)
					m.appendChild(s);
				document.body.appendChild(k);
				window.focus();
				if (n)
					n.focus();
				else
					o && o.focus();
				if (a.html !== f) {
					m = c.util.getIframeDoc(p);
					s = c.util.getFullHtml(j);
					m.open();
					m.write(s);
					m.close();
					c.util.innerHtml(m.body, a.html)
				} else if (a.url !== f)
					p.src = a.url;
				else {
					m = "id=" + escape(j) + "&ver=" + escape(c.version);
					if (a.file === f)
						p.src = c.g[j].pluginsPath + a.cmd + ".html?" + m;
					else {
						m = (/\?/.test(a.file) ? "&" : "?") + m;
						p.src = c.g[j].pluginsPath + a.file + m
					}
				}
				c.g[j].maskDiv.style.width = c.util.getDocumentWidth() + "px";
				c.g[j].maskDiv.style.height = c.util.getDocumentHeight() + "px";
				c.g[j].maskDiv.style.display = "block";
				this.iframe = p;
				this.loading = l.table;
				this.noButton = o;
				this.yesButton = n;
				this.previewButton = r;
				this.div = k;
				c.g[j].dialogStack.push(this);
				c.g[j].dialog = p;
				c.g[j].yesButton = n;
				c.g[j].noButton = o;
				c.g[j].previewButton = r;
				a.loadingMode || c.util.hideLoadingPage(j);
				this.afterShow && this.afterShow(j);
				c.g[j].afterDialogCreate && c.g[j].afterDialogCreate(j)
			}
		};
		c.toolbar = {
			updateState : function(a) {
				for ( var b = [ "justifyleft", "justifycenter", "justifyright",
						"justifyfull", "insertorderedlist",
						"insertunorderedlist", "indent", "outdent",
						"subscript", "superscript", "bold", "italic",
						"underline", "strikethrough" ], d = 0; d < b.length; d++) {
					var e = b[d], g = false;
					try {
						g = c.g[a].iframeDoc.queryCommandState(e)
					} catch (i) {
					}
					g ? c.toolbar.select(a, e) : c.toolbar.unselect(a, e)
				}
			},
			isSelected : function(a, b) {
				return c.plugin[b] && c.plugin[b].isSelected ? true : false
			},
			select : function(a, b) {
				if (c.g[a].toolbarIcon[b]) {
					var d = c.g[a].toolbarIcon[b][0];
					d.className = "ke-icon ke-icon-selected";
					d.onmouseover = null;
					d.onmouseout = null
				}
			},
			unselect : function(a, b) {
				if (c.g[a].toolbarIcon[b]) {
					var d = c.g[a].toolbarIcon[b][0];
					d.className = "ke-icon";
					d.onmouseover = function() {
						this.className = "ke-icon ke-icon-on"
					};
					d.onmouseout = function() {
						this.className = "ke-icon"
					}
				}
			},
			_setAttr : function(a, b, d) {
				b.className = "ke-icon";
				b.href = "javascript:;";
				b.onclick = function(e) {
					e = e || window.event;
					var g = c.g[a].hideDiv.firstChild;
					g && g.getAttribute("name") == d ? c.hideMenu(a) : c.util
							.click(a, d);
					e.preventDefault && e.preventDefault();
					e.stopPropagation && e.stopPropagation();
					if (e.cancelBubble !== f)
						e.cancelBubble = true;
					return false
				};
				b.onmouseover = function() {
					this.className = "ke-icon ke-icon-on"
				};
				b.onmouseout = function() {
					this.className = "ke-icon"
				};
				b.hidefocus = true;
				b.title = c.lang[d]
			},
			able : function(a, b) {
				var d = this;
				c.each(c.g[a].toolbarIcon, function(e, g) {
					if (!c.util.inArray(e, b)) {
						var i = g[1];
						d._setAttr(a, g[0], e);
						c.util.setOpacity(i, 100)
					}
				})
			},
			disable : function(a, b) {
				c.each(c.g[a].toolbarIcon, function(d, e) {
					if (!c.util.inArray(d, b)) {
						var g = e[0], i = e[1];
						g.className = "ke-icon ke-icon-disabled";
						c.util.setOpacity(i, 50);
						g.onclick = null;
						g.onmouseover = null;
						g.onmouseout = null
					}
				})
			},
			create : function(a) {
				var b = c.util.arrayToHash(c.setting.items);
				c.g[a].toolbarIcon = [];
				var d = c.util.createTable(), e = d.table;
				e.className = "ke-toolbar";
				e.oncontextmenu = function() {
					return false
				};
				e.onmousedown = function() {
					return false
				};
				e.onmousemove = function() {
					return false
				};
				d = d.cell;
				var g = c.g[a].items.length, i = 0, j;
				c.g[a].toolbarHeight = c.g[a].toolbarLineHeight;
				for ( var k = 0; k < g; k++) {
					var l = c.g[a].items[k];
					if (k == 0 || l == "-") {
						var m = c.$$("table");
						m.cellPadding = 0;
						m.cellSpacing = 0;
						m.border = 0;
						m.className = "ke-toolbar-table";
						j = m.insertRow(0);
						i = 0;
						d.appendChild(m);
						if (l == "-") {
							c.g[a].toolbarHeight += c.g[a].toolbarLineHeight;
							continue
						}
					}
					m = j.insertCell(i);
					m.hideforcus = true;
					i++;
					if (l == "|") {
						l = c.$$("div");
						l.className = "ke-toolbar-separator";
						m.appendChild(l)
					} else {
						var p = c.$$("a");
						p.tabIndex = -1;
						this._setAttr(a, p, l);
						var o = c.$$("span");
						o.className = typeof b[l] == "undefined" ? "ke-common-icon ke-icon-"
								+ l
								: "ke-common-icon ke-common-icon-url ke-icon-"
										+ l;
						p.appendChild(o);
						m.appendChild(p);
						c.g[a].toolbarIcon[l] = [ p, o ];
						c.toolbar.isSelected(a, l) && c.toolbar.select(a, l)
					}
				}
				return e
			}
		};
		c.history = {
			addStackData : function(a, b) {
				var d = "";
				if (a.length > 0)
					d = a[a.length - 1];
				if (a.length == 0 || b !== d)
					a.push(b)
			},
			add : function(a, b) {
				var d = c.g[a], e = c.util.getSrcData(a);
				if (d.undoStack.length > 0)
					if (Math.abs(e.length
							- d.undoStack[d.undoStack.length - 1].length) < b)
						return;
				this.addStackData(d.undoStack, e)
			},
			undo : function(a) {
				var b = c.g[a];
				if (b.undoStack.length != 0) {
					var d = c.util.getSrcData(a);
					this.addStackData(b.redoStack, d);
					var e = b.undoStack.pop();
					if (d === e && b.undoStack.length > 0)
						e = b.undoStack.pop();
					e = c.util.toData(a, e);
					if (b.wyswygMode)
						c.util.innerHtml(b.iframeDoc.body, c.util
								.execSetHtmlHooks(a, e));
					else
						b.newTextarea.value = e
				}
			},
			redo : function(a) {
				var b = c.g[a];
				if (b.redoStack.length != 0) {
					var d = c.util.getSrcData(a);
					this.addStackData(b.undoStack, d);
					d = b.redoStack.pop();
					d = c.util.toData(a, d);
					if (b.wyswygMode)
						c.util.innerHtml(b.iframeDoc.body, c.util
								.execSetHtmlHooks(a, d));
					else
						b.newTextarea.value = d
				}
			}
		};
		c.readonly = function(a, b) {
			b = b == f ? true : b;
			var d = c.g[a];
			if (c.browser.IE)
				d.iframeDoc.body.contentEditable = b ? "false" : "true";
			else
				d.iframeDoc.designMode = b ? "off" : "on"
		};
		c.focus = function(a, b) {
			b = (b || "").toLowerCase();
			if (c.g[a].container) {
				c.util.focus(a);
				if (b === "end") {
					c.util.setSelection(a);
					if (c.g[a].sel) {
						var d = c.g[a].keSel, e = c.g[a].keRange;
						e.selectTextNode(c.g[a].iframeDoc.body);
						e.collapse(false);
						d.addRange(e)
					}
				}
			}
		};
		c.blur = function(a) {
			a = c.g[a];
			if (a.container)
				if (c.browser.IE) {
					var b = c.$$("input");
					b.type = "text";
					a.container.appendChild(b);
					b.focus();
					a.container.removeChild(b)
				} else
					a.wyswygMode ? a.iframeWin.blur() : a.newTextarea.blur()
		};
		c.html = function(a, b) {
			if (b === f)
				return c.util.getData(a);
			else if (c.g[a].container) {
				c.util.setFullHtml(a, b);
				c.focus(a)
			}
		};
		c.text = function(a, b) {
			if (b === f) {
				b = c.html(a);
				b = b.replace(/<.*?>/ig, "");
				b = b.replace(/&nbsp;/ig, " ");
				return b = c.util.trim(b)
			} else
				c.html(a, c.util.escape(b))
		};
		c.insertHtml = function(a, b) {
			if (c.g[a].container)
				if (c.g[a].range) {
					c.focus(a);
					c.util.selection(a);
					c.util.insertHtml(a, b)
				} else
					c.appendHtml(a, b)
		};
		c.appendHtml = function(a, b) {
			c.html(a, c.html(a) + b)
		};
		c.isEmpty = function(a) {
			return c.util.isEmpty(a)
		};
		c.selectedHtml = function(a) {
			var b = c.g[a].range;
			if (!b)
				return "";
			var d = "";
			if (c.browser.IE)
				d = b.item ? b.item(0).outerHTML : b.htmlText;
			else {
				d = c.$$("div", c.g[a].iframeDoc);
				d.appendChild(b.cloneContents());
				d = d.innerHTML
			}
			return c.util.toData(a, d)
		};
		c.count = function(a, b) {
			b = (b || "html").toLowerCase();
			if (b === "html")
				return c.html(a).length;
			else if (b === "text") {
				var d = c.util.getPureData(a);
				d = d.replace(/<(?:img|embed).*?>/ig, "K");
				d = d.replace(/\r\n|\n|\r/g, "");
				d = c.util.trim(d);
				return d.length
			}
			return 0
		};
		c.sync = function(a) {
			return c.util.setData(a)
		};
		c.remove = function(a, b) {
			var d = c.g[a];
			if (!d.container)
				return false;
			b = typeof b == "undefined" ? 0 : b;
			c.util.setData(a);
			for ( var e = d.container, g = d.eventStack, i = 0, j = g.length; i < j; i++) {
				var k = g[i];
				k && c.event.remove(k.el, k.type, k.fn, a)
			}
			d.iframeDoc.src = "javascript:false";
			d.iframe.parentNode.removeChild(d.iframe);
			if (b == 1)
				document.body.removeChild(e);
			else {
				g = d.srcTextarea;
				g.parentNode.removeChild(e);
				if (b == 0)
					g.style.display = ""
			}
			document.body.removeChild(d.hideDiv);
			document.body.removeChild(d.maskDiv);
			d.container = null;
			d.dialogStack = [];
			d.contextmenuItems = [];
			d.getHtmlHooks = [];
			d.setHtmlHooks = [];
			d.onchangeHandlerStack = [];
			d.eventStack = []
		};
		c.create = function(a, b) {
			function d() {
				c.hideMenu(a)
			}
			function e() {
				c.toolbar.updateState(a)
			}
			function g() {
				c.g[a].afterFocus && c.g[a].afterFocus(a)
			}
			function i() {
				c.g[a].afterBlur && c.g[a].afterBlur(a)
			}
			function j() {
				c.util.setSelection(a)
			}
			c.g[a].beforeCreate && c.g[a].beforeCreate(a);
			if (c.browser.IE && c.browser.VERSION < 7)
				try {
					document.execCommand("BackgroundImageCache", false, true)
				} catch (k) {
				}
			var l = c.$(a) || document.getElementsByName(a)[0];
			b = typeof b == "undefined" ? 0 : b;
			if (!(b == 0 && c.g[a].container)) {
				var m = c.g[a].width || l.style.width || l.offsetWidth + "px", p = c.g[a].height
						|| l.style.height || l.offsetHeight + "px", o = c.util
						.createTable(), n = o.table;
				n.className = "ke-container";
				n.style.width = m;
				n.style.height = p;
				var r = o.cell;
				r.className = "ke-toolbar-outer";
				var s = n.insertRow(1).insertCell(0);
				s.className = "ke-textarea-outer";
				o = c.util.createTable();
				var t = o.table;
				t.className = "ke-textarea-table";
				var q = o.cell;
				s.appendChild(t);
				var w = n.insertRow(2).insertCell(0);
				w.className = "ke-bottom-outer";
				l.style.display = "none";
				b == 1 ? document.body.appendChild(n) : l.parentNode
						.insertBefore(n, l);
				s = c.toolbar.create(a);
				s.style.height = c.g[a].toolbarHeight + "px";
				r.appendChild(s);
				o = c.g[a].iframe || c.$$("iframe");
				o.tabIndex = c.g[a].tabIndex || l.tabIndex;
				o.className = "ke-iframe";
				o.setAttribute("frameBorder", "0");
				r = c.$$("textarea");
				r.tabIndex = o.tabIndex;
				r.className = "ke-textarea";
				r.style.display = "none";
				c.g[a].container = n;
				c.g[a].iframe = o;
				c.g[a].newTextarea = r;
				c.util.resize(a, m, p);
				q.appendChild(o);
				q.appendChild(r);
				q = c.$$("table");
				q.className = "ke-bottom";
				q.cellPadding = 0;
				q.cellSpacing = 0;
				q.border = 0;
				q.style.height = c.g[a].statusbarHeight + "px";
				var u = q.insertRow(0), x = u.insertCell(0);
				x.className = "ke-bottom-left";
				var A = c.$$("span");
				A.className = "ke-bottom-left-img";
				if (c.g[a].config.resizeMode == 0 || b == 1) {
					x.style.cursor = "default";
					A.style.visibility = "hidden"
				}
				x.appendChild(A);
				u = u.insertCell(1);
				u.className = "ke-bottom-right";
				A = c.$$("span");
				A.className = "ke-bottom-right-img";
				if (c.g[a].config.resizeMode == 0 || b == 1) {
					u.style.cursor = "default";
					A.style.visibility = "hidden"
				} else if (c.g[a].config.resizeMode == 1) {
					u.style.cursor = "s-resize";
					A.style.visibility = "hidden"
				}
				u.appendChild(A);
				w.appendChild(q);
				w = c.$$("div");
				w.className = "ke-reset";
				w.style.display = "none";
				A = c.$$("div");
				A.className = "ke-mask";
				c.util.setOpacity(A, 50);
				c.event.bind(A, "click", function() {
				}, a);
				c.event.bind(A, "mousedown", function() {
				}, a);
				document.body.appendChild(w);
				document.body.appendChild(A);
				c.util.setDefaultPlugin(a);
				var B = o.contentWindow, y = c.util.getIframeDoc(o);
				if (!c.browser.IE)
					y.designMode = "on";
				var v = c.util.getFullHtml(a);
				y.open();
				y.write(v);
				y.close();
				if (!c.g[a].wyswygMode) {
					r.value = c.util.execSetHtmlHooks(a, l.value);
					r.style.display = "block";
					o.style.display = "none";
					c.toolbar.disable(a, [ "source", "fullscreen" ]);
					c.toolbar.select(a, "source")
				}
				if (c.g[a].syncType == "form")
					for (o = l; o = o.parentNode;)
						if (o.nodeName.toLowerCase() == "form") {
							c.event.add(o, "submit", function() {
								c.sync(a)
							}, a);
							break
						}
				c.browser.WEBKIT && c.event.add(y, "click", function(z) {
					c.util.selectImageWebkit(a, z, true)
				}, a);
				c.browser.IE && c.event.add(y, "keydown", function(z) {
					if (z.keyCode == 8) {
						z = c.g[a].range;
						if (z.item) {
							z = z.item(0);
							z.parentNode.removeChild(z);
							c.util.execOnchangeHandler(a);
							c.event.stop(a);
							return false
						}
					}
				}, a);
				c.event.add(y, "mousedown", d, a);
				c.event.add(y, "click", e, a);
				c.event.input(y, e, a);
				c.event.bind(r, "click", d, a);
				c.event.add(document, "click", d, a);
				c.event.add(B, "focus", g);
				c.event.add(r, "focus", g);
				c.event.add(B, "blur", i);
				c.event.add(r, "blur", i);
				c.g[a].toolbarTable = s;
				c.g[a].textareaTable = t;
				c.g[a].srcTextarea = l;
				c.g[a].bottom = q;
				c.g[a].hideDiv = w;
				c.g[a].maskDiv = A;
				c.g[a].iframeWin = B;
				c.g[a].iframeDoc = y;
				c.g[a].width = m;
				c.g[a].height = p;
				c.util.drag(a, u, n, function(z, D, C, E, F, G) {
					if (c.g[a].resizeMode == 2)
						c.util.resize(a, C + G + "px", E + F + "px", true);
					else
						c.g[a].resizeMode == 1
								&& c.util.resize(a, C + "px", E + F + "px",
										true, false)
				});
				c.util.drag(a, x, n, function(z, D, C, E, F) {
					c.g[a].resizeMode > 0
							&& c.util.resize(a, C + "px", E + F + "px", true,
									false)
				});
				c.each(c.plugin, function(z, D) {
					D.init && D.init(a)
				});
				c.g[a].getHtmlHooks.push(function(z) {
					z = z.replace(/(<[^>]*)kesrc="([^"]+)"([^>]*>)/ig,
							function(D, C, E) {
								D = D.replace(/(\s+(?:href|src)=")[^"]+(")/i,
										"$1" + E + "$2");
								return D = D.replace(/\s+kesrc="[^"]+"/i, "")
							});
					return z = z.replace(
							/(<[^>]+\s)ke-(on\w+="[^"]+"[^>]*>)/ig, function(D,
									C, E) {
								return C + E
							})
				});
				c.g[a].setHtmlHooks.push(function(z) {
					z = z.replace(/(<[^>]*)(href|src)="([^"]+)"([^>]*>)/ig,
							function(D, C, E, F, G) {
								if (D.match(/\skesrc="[^"]+"/i))
									return D;
								return C + E + '="' + F + '" kesrc="' + F + '"'
										+ G
							});
					return z = z.replace(/(<[^>]+\s)(on\w+="[^"]+"[^>]*>)/ig,
							function(D, C, E) {
								return C + "ke-" + E
							})
				});
				c.util.addContextmenuEvent(a);
				c.util.addNewlineEvent(a);
				c.util.addTabEvent(a);
				c.event.input(y, j, a);
				c.event.add(y, "mouseup", j, a);
				c.event.add(document, "mousedown", j, a);
				c.onchange(a, function(z) {
					if (c.g[z].autoSetDataMode || c.g[z].syncType == "auto") {
						c.util.setData(z);
						c.g[z].afterSetData && c.g[z].afterSetData(z)
					}
					c.g[z].afterChange && c.g[z].afterChange(z);
					c.history.add(z, c.g[z].minChangeSize)
				});
				if (c.browser.IE) {
					y.body.disabled = true;
					c.readonly(a, false);
					y.body.removeAttribute("disabled")
				}
				c.util.setFullHtml(a, l.value);
				c.history.add(a, 0);
				b > 0 && c.util.focus(a);
				c.g[a].afterCreate && c.g[a].afterCreate(a)
			}
		};
		c.onchange = function(a, b) {
			function d() {
				b(a)
			}
			var e = c.g[a];
			e.onchangeHandlerStack.push(d);
			c.event.input(e.iframeDoc, d, a);
			c.event.input(e.newTextarea, d, a);
			c.event.add(e.iframeDoc, "mouseup", function() {
				window.setTimeout(function() {
					b(a)
				}, 0)
			}, a)
		};
		var h = true;
		c.init = function(a) {
			var b = c.g[a.id] = a;
			b.config = {};
			b.undoStack = [];
			b.redoStack = [];
			b.dialogStack = [];
			b.contextmenuItems = [];
			b.getHtmlHooks = [];
			b.setHtmlHooks = [];
			b.onchangeHandlerStack = [];
			b.eventStack = [];
			c.each(c.setting, function(d, e) {
				b[d] = typeof a[d] == "undefined" ? e : a[d];
				b.config[d] = b[d]
			});
			if (b.loadStyleMode && h) {
				c.util.loadStyle(b.skinsPath + b.skinType + ".css");
				h = false
			}
		};
		c.show = function(a) {
			c.init(a);
			c.event.ready(function() {
				c.create(a.id)
			})
		};
		if (window.KE === f)
			window.KE = c;
		window.KindEditor = c
	}
})();
(function(f) {
	f.langType = "zh_CN";
	f.lang = {
		source : "HTML\u4ee3\u7801",
		undo : "\u540e\u9000(Ctrl+Z)",
		redo : "\u524d\u8fdb(Ctrl+Y)",
		cut : "\u526a\u5207(Ctrl+X)",
		copy : "\u590d\u5236(Ctrl+C)",
		paste : "\u7c98\u8d34(Ctrl+V)",
		plainpaste : "\u7c98\u8d34\u4e3a\u65e0\u683c\u5f0f\u6587\u672c",
		wordpaste : "\u4eceWord\u7c98\u8d34",
		selectall : "\u5168\u9009",
		justifyleft : "\u5de6\u5bf9\u9f50",
		justifycenter : "\u5c45\u4e2d",
		justifyright : "\u53f3\u5bf9\u9f50",
		justifyfull : "\u4e24\u7aef\u5bf9\u9f50",
		insertorderedlist : "\u7f16\u53f7",
		insertunorderedlist : "\u9879\u76ee\u7b26\u53f7",
		indent : "\u589e\u52a0\u7f29\u8fdb",
		outdent : "\u51cf\u5c11\u7f29\u8fdb",
		subscript : "\u4e0b\u6807",
		superscript : "\u4e0a\u6807",
		title : "\u6807\u9898",
		fontname : "\u5b57\u4f53",
		fontsize : "\u6587\u5b57\u5927\u5c0f",
		textcolor : "\u6587\u5b57\u989c\u8272",
		bgcolor : "\u6587\u5b57\u80cc\u666f",
		bold : "\u7c97\u4f53(Ctrl+B)",
		italic : "\u659c\u4f53(Ctrl+I)",
		underline : "\u4e0b\u5212\u7ebf(Ctrl+U)",
		strikethrough : "\u5220\u9664\u7ebf",
		removeformat : "\u5220\u9664\u683c\u5f0f",
		image : "\u56fe\u7247",
		flash : "\u63d2\u5165Flash",
		media : "\u63d2\u5165\u591a\u5a92\u4f53",
		table : "\u63d2\u5165\u8868\u683c",
		hr : "\u63d2\u5165\u6a2a\u7ebf",
		emoticons : "\u63d2\u5165\u8868\u60c5",
		link : "\u8d85\u7ea7\u94fe\u63a5",
		unlink : "\u53d6\u6d88\u8d85\u7ea7\u94fe\u63a5",
		fullscreen : "\u5168\u5c4f\u663e\u793a",
		about : "\u5173\u4e8e",
		print : "\u6253\u5370",
		fileManager : "\u6d4f\u89c8\u670d\u52a1\u5668",
		advtable : "\u8868\u683c",
		yes : "\u786e\u5b9a",
		no : "\u53d6\u6d88",
		close : "\u5173\u95ed",
		editImage : "\u56fe\u7247\u5c5e\u6027",
		deleteImage : "\u5220\u9664\u56fe\u7247",
		editLink : "\u8d85\u7ea7\u94fe\u63a5\u5c5e\u6027",
		deleteLink : "\u53d6\u6d88\u8d85\u7ea7\u94fe\u63a5",
		tableprop : "\u8868\u683c\u5c5e\u6027",
		tableinsert : "\u63d2\u5165\u8868\u683c",
		tabledelete : "\u5220\u9664\u8868\u683c",
		tablecolinsertleft : "\u5de6\u4fa7\u63d2\u5165\u5217",
		tablecolinsertright : "\u53f3\u4fa7\u63d2\u5165\u5217",
		tablerowinsertabove : "\u4e0a\u65b9\u63d2\u5165\u884c",
		tablerowinsertbelow : "\u4e0b\u65b9\u63d2\u5165\u884c",
		tablecoldelete : "\u5220\u9664\u5217",
		tablerowdelete : "\u5220\u9664\u884c",
		noColor : "\u65e0\u989c\u8272",
		invalidImg : "\u8bf7\u8f93\u5165\u6709\u6548\u7684URL\u5730\u5740\u3002\n\u53ea\u5141\u8bb8jpg,gif,bmp,png\u683c\u5f0f\u3002",
		invalidMedia : "\u8bf7\u8f93\u5165\u6709\u6548\u7684URL\u5730\u5740\u3002\n\u53ea\u5141\u8bb8swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb\u683c\u5f0f\u3002",
		invalidWidth : "\u5bbd\u5ea6\u5fc5\u987b\u4e3a\u6570\u5b57\u3002",
		invalidHeight : "\u9ad8\u5ea6\u5fc5\u987b\u4e3a\u6570\u5b57\u3002",
		invalidBorder : "\u8fb9\u6846\u5fc5\u987b\u4e3a\u6570\u5b57\u3002",
		invalidUrl : "\u8bf7\u8f93\u5165\u6709\u6548\u7684URL\u5730\u5740\u3002",
		invalidRows : "\u884c\u6570\u4e3a\u5fc5\u9009\u9879\uff0c\u53ea\u5141\u8bb8\u8f93\u5165\u5927\u4e8e0\u7684\u6570\u5b57\u3002",
		invalidCols : "\u5217\u6570\u4e3a\u5fc5\u9009\u9879\uff0c\u53ea\u5141\u8bb8\u8f93\u5165\u5927\u4e8e0\u7684\u6570\u5b57\u3002",
		invalidPadding : "\u8fb9\u8ddd\u5fc5\u987b\u4e3a\u6570\u5b57\u3002",
		invalidSpacing : "\u95f4\u8ddd\u5fc5\u987b\u4e3a\u6570\u5b57\u3002",
		invalidBorder : "\u8fb9\u6846\u5fc5\u987b\u4e3a\u6570\u5b57\u3002",
		pleaseInput : "\u8bf7\u8f93\u5165\u5185\u5bb9\u3002",
		invalidJson : "\u670d\u52a1\u5668\u53d1\u751f\u6545\u969c\u3002",
		cutError : "\u60a8\u7684\u6d4f\u89c8\u5668\u5b89\u5168\u8bbe\u7f6e\u4e0d\u5141\u8bb8\u4f7f\u7528\u526a\u5207\u64cd\u4f5c\uff0c\u8bf7\u4f7f\u7528\u5feb\u6377\u952e(Ctrl+X)\u6765\u5b8c\u6210\u3002",
		copyError : "\u60a8\u7684\u6d4f\u89c8\u5668\u5b89\u5168\u8bbe\u7f6e\u4e0d\u5141\u8bb8\u4f7f\u7528\u590d\u5236\u64cd\u4f5c\uff0c\u8bf7\u4f7f\u7528\u5feb\u6377\u952e(Ctrl+C)\u6765\u5b8c\u6210\u3002",
		pasteError : "\u60a8\u7684\u6d4f\u89c8\u5668\u5b89\u5168\u8bbe\u7f6e\u4e0d\u5141\u8bb8\u4f7f\u7528\u7c98\u8d34\u64cd\u4f5c\uff0c\u8bf7\u4f7f\u7528\u5feb\u6377\u952e(Ctrl+V)\u6765\u5b8c\u6210\u3002"
	};
	var c = f.lang.plugins = {};
	c.about = {
		version : f.version,
		title : "HTML\u53ef\u89c6\u5316\u7f16\u8f91\u5668"
	};
	c.plainpaste = {
		comment : "\u8bf7\u4f7f\u7528\u5feb\u6377\u952e(Ctrl+V)\u628a\u5185\u5bb9\u7c98\u8d34\u5230\u4e0b\u9762\u7684\u65b9\u6846\u91cc\u3002"
	};
	c.wordpaste = {
		comment : "\u8bf7\u4f7f\u7528\u5feb\u6377\u952e(Ctrl+V)\u628a\u5185\u5bb9\u7c98\u8d34\u5230\u4e0b\u9762\u7684\u65b9\u6846\u91cc\u3002"
	};
	c.link = {
		url : "URL\u5730\u5740",
		linkType : "\u6253\u5f00\u7c7b\u578b",
		newWindow : "\u65b0\u7a97\u53e3",
		selfWindow : "\u5f53\u524d\u7a97\u53e3"
	};
	c.flash = {
		url : "Flash\u5730\u5740",
		width : "\u5bbd\u5ea6",
		height : "\u9ad8\u5ea6"
	};
	c.media = {
		url : "\u5a92\u4f53\u6587\u4ef6\u5730\u5740",
		width : "\u5bbd\u5ea6",
		height : "\u9ad8\u5ea6",
		autostart : "\u81ea\u52a8\u64ad\u653e"
	};
	c.image = {
		remoteImage : "\u8fdc\u7a0b\u56fe\u7247",
		localImage : "\u672c\u5730\u4e0a\u4f20",
		remoteUrl : "\u56fe\u7247\u5730\u5740",
		localUrl : "\u56fe\u7247\u5730\u5740",
		size : "\u56fe\u7247\u5927\u5c0f",
		width : "\u5bbd",
		height : "\u9ad8",
		resetSize : "\u91cd\u7f6e\u5927\u5c0f",
		align : "\u5bf9\u9f50\u65b9\u5f0f",
		defaultAlign : "\u9ed8\u8ba4\u65b9\u5f0f",
		leftAlign : "\u5de6\u5bf9\u9f50",
		rightAlign : "\u53f3\u5bf9\u9f50",
		imgTitle : "\u56fe\u7247\u8bf4\u660e",
		viewServer : "\u6d4f\u89c8..."
	};
	c.file_manager = {
		emptyFolder : "\u7a7a\u6587\u4ef6\u5939",
		moveup : "\u79fb\u5230\u4e0a\u4e00\u7ea7\u6587\u4ef6\u5939",
		viewType : "\u663e\u793a\u65b9\u5f0f\uff1a",
		viewImage : "\u7f29\u7565\u56fe",
		listImage : "\u8be6\u7ec6\u4fe1\u606f",
		orderType : "\u6392\u5e8f\u65b9\u5f0f\uff1a",
		fileName : "\u540d\u79f0",
		fileSize : "\u5927\u5c0f",
		fileType : "\u7c7b\u578b"
	};
	c.advtable = {
		cells : "\u5355\u5143\u683c\u6570",
		rows : "\u884c\u6570",
		cols : "\u5217\u6570",
		size : "\u8868\u683c\u5927\u5c0f",
		width : "\u5bbd\u5ea6",
		height : "\u9ad8\u5ea6",
		percent : "%",
		px : "px",
		space : "\u8fb9\u8ddd\u95f4\u8ddd",
		padding : "\u8fb9\u8ddd",
		spacing : "\u95f4\u8ddd",
		align : "\u5bf9\u9f50\u65b9\u5f0f",
		alignDefault : "\u9ed8\u8ba4",
		alignLeft : "\u5de6\u5bf9\u9f50",
		alignCenter : "\u5c45\u4e2d",
		alignRight : "\u53f3\u5bf9\u9f50",
		border : "\u8868\u683c\u8fb9\u6846",
		borderWidth : "\u8fb9\u6846",
		borderColor : "\u989c\u8272",
		backgroundColor : "\u80cc\u666f\u989c\u8272"
	};
	c.title = {
		h1 : "\u6807\u9898 1",
		h2 : "\u6807\u9898 2",
		h3 : "\u6807\u9898 3",
		h4 : "\u6807\u9898 4",
		p : "\u6b63 \u6587"
	};
	c.fontname = {
		fontName : {
			SimSun : "\u5b8b\u4f53",
			NSimSun : "\u65b0\u5b8b\u4f53",
			FangSong_GB2312 : "\u4eff\u5b8b_GB2312",
			KaiTi_GB2312 : "\u6977\u4f53_GB2312",
			SimHei : "\u9ed1\u4f53",
			"Microsoft YaHei" : "\u5fae\u8f6f\u96c5\u9ed1",
			Arial : "Arial",
			"Arial Black" : "Arial Black",
			"Times New Roman" : "Times New Roman",
			"Courier New" : "Courier New",
			Tahoma : "Tahoma",
			Verdana : "Verdana"
		}
	}
})(KindEditor);
(function(f, c) {
	f.plugin.about = {
		click : function(h) {
			f.util.selection(h);
			(new f.dialog( {
				id : h,
				cmd : "about",
				file : "about.html",
				width : 300,
				height : 70,
				loadingMode : true,
				title : f.lang.about,
				noButton : f.lang.close
			})).show()
		}
	};
	f.plugin.undo = {
		init : function(h) {
			f.event.ctrl(f.g[h].iframeDoc, "Z", function() {
				f.plugin.undo.click(h);
				f.util.focus(h)
			}, h);
			f.event.ctrl(f.g[h].newTextarea, "Z", function() {
				f.plugin.undo.click(h);
				f.util.focus(h)
			}, h)
		},
		click : function(h) {
			f.history.undo(h);
			f.util.execOnchangeHandler(h)
		}
	};
	f.plugin.redo = {
		init : function(h) {
			f.event.ctrl(f.g[h].iframeDoc, "Y", function() {
				f.plugin.redo.click(h);
				f.util.focus(h)
			}, h);
			f.event.ctrl(f.g[h].newTextarea, "Y", function() {
				f.plugin.redo.click(h);
				f.util.focus(h)
			}, h)
		},
		click : function(h) {
			f.history.redo(h);
			f.util.execOnchangeHandler(h)
		}
	};
	f.plugin.cut = {
		click : function(h) {
			try {
				if (!f.g[h].iframeDoc.queryCommandSupported("cut"))
					throw "e";
			} catch (a) {
				alert(f.lang.cutError);
				return
			}
			f.util.execCommand(h, "cut", null)
		}
	};
	f.plugin.copy = {
		click : function(h) {
			try {
				if (!f.g[h].iframeDoc.queryCommandSupported("copy"))
					throw "e";
			} catch (a) {
				alert(f.lang.copyError);
				return
			}
			f.util.execCommand(h, "copy", null)
		}
	};
	f.plugin.paste = {
		click : function(h) {
			try {
				if (!f.g[h].iframeDoc.queryCommandSupported("paste"))
					throw "e";
			} catch (a) {
				alert(f.lang.pasteError);
				return
			}
			f.util.execCommand(h, "paste", null)
		}
	};
	f.plugin.plainpaste = {
		click : function(h) {
			f.util.selection(h);
			this.dialog = new f.dialog( {
				id : h,
				cmd : "plainpaste",
				file : "plainpaste.html",
				width : 450,
				height : 300,
				loadingMode : true,
				title : f.lang.plainpaste,
				yesButton : f.lang.yes,
				noButton : f.lang.no
			});
			this.dialog.show()
		},
		exec : function(h) {
			var a = f.util.getIframeDoc(this.dialog.iframe);
			a = f.$("textArea", a).value;
			a = f.util.escape(a);
			a = a.replace(/ /g, "&nbsp;");
			a = f.g[h].newlineTag == "p" ? a.replace(/^/, "<p>").replace(/$/,
					"</p>").replace(/\r\n|\n|\r/g, "</p><p>") : a.replace(
					/\r\n|\n|\r/g, "<br />$&");
			f.util.insertHtml(h, a);
			this.dialog.hide();
			f.util.focus(h)
		}
	};
	f.plugin.wordpaste = {
		click : function(h) {
			f.util.selection(h);
			this.dialog = new f.dialog( {
				id : h,
				cmd : "wordpaste",
				file : "wordpaste.html",
				width : 450,
				height : 300,
				loadingMode : true,
				title : f.lang.wordpaste,
				yesButton : f.lang.yes,
				noButton : f.lang.no
			});
			this.dialog.show()
		},
		exec : function(h) {
			var a = f.util.getIframeDoc(this.dialog.iframe);
			a = f.$("wordIframe", a);
			a = f.util.getIframeDoc(a).body.innerHTML;
			a = a.replace(/<meta(\n|.)*?>/ig, "");
			a = a.replace(/<!(\n|.)*?>/ig, "");
			a = a.replace(/<style[^>]*>(\n|.)*?<\/style>/ig, "");
			a = a.replace(/<script[^>]*>(\n|.)*?<\/script>/ig, "");
			a = a.replace(/<w:[^>]+>(\n|.)*?<\/w:[^>]+>/ig, "");
			a = a.replace(/<xml>(\n|.)*?<\/xml>/ig, "");
			a = a.replace(/\r\n|\n|\r/ig, "");
			a = f.util.execGetHtmlHooks(h, a);
			a = f.format.getHtml(a, f.g[h].htmlTags, f.g[h].urlType);
			f.util.insertHtml(h, a);
			this.dialog.hide();
			f.util.focus(h)
		}
	};
	f.plugin.fullscreen = {
		click : function(h) {
			var a = f.g[h], b = this, d = function() {
				var k = f.util.getDocumentElement();
				a.width = k.clientWidth + "px";
				a.height = k.clientHeight + "px"
			}, e = "", g = function() {
				if (b.isSelected) {
					var k = f.util.getDocumentElement();
					k = [ k.clientWidth, k.clientHeight ].join("");
					if (e != k) {
						e = k;
						d();
						f.util.resize(h, a.width, a.height)
					}
				}
			};
			if (this.isSelected) {
				this.isSelected = false;
				f.util.setData(h);
				f.remove(h, 1);
				a.width = this.width;
				a.height = this.height;
				f.create(h, 2);
				document.body.parentNode.style.overflow = "auto";
				f.event.remove(window, "resize", g);
				a.resizeMode = a.config.resizeMode;
				f.toolbar.unselect(h, "fullscreen")
			} else {
				this.isSelected = true;
				this.width = a.container.style.width;
				this.height = a.container.style.height;
				f.util.setData(h);
				f.remove(h, 2);
				document.body.parentNode.style.overflow = "hidden";
				d();
				f.create(h, 1);
				var i = f.util.getScrollPos(), j = a.container;
				j.style.position = "absolute";
				j.style.left = i.x + "px";
				j.style.top = i.y + "px";
				j.style.zIndex = 19811211;
				f.event.add(window, "resize", g);
				a.resizeMode = 0;
				f.toolbar.select(h, "fullscreen")
			}
		}
	};
	f.plugin.bgcolor = {
		click : function(h) {
			f.util.selection(h);
			var a = f.queryCommandValue(f.g[h].iframeDoc, "bgcolor");
			this.menu = new f.menu( {
				id : h,
				cmd : "bgcolor"
			});
			this.menu.picker(a)
		},
		exec : function(h, a) {
			var b = new f.cmd(h);
			a == "" ? b.remove( {
				span : [ ".background-color" ]
			}) : b.wrap("span", [ {
				".background-color" : a
			} ]);
			f.util.execOnchangeHandler(h);
			this.menu.hide();
			f.util.focus(h)
		}
	};
	f.plugin.fontname = {
		click : function(h) {
			var a = f.lang.plugins.fontname.fontName;
			f.util.selection(h);
			var b = new f.menu( {
				id : h,
				cmd : "fontname",
				width : 150
			}), d = f.queryCommandValue(f.g[h].iframeDoc, "fontname");
			f.each(a, function(e, g) {
				b.add('<span class="ke-reset" style="font-family: ' + e + ';">'
						+ g + "</span>", function() {
					f.plugin.fontname.exec(h, e)
				}, {
					checked : d === e.toLowerCase() || d === g.toLowerCase()
				})
			});
			b.show();
			this.menu = b
		},
		exec : function(h, a) {
			(new f.cmd(h)).wrap("span", [ {
				".font-family" : a
			} ]);
			f.util.execOnchangeHandler(h);
			this.menu.hide();
			f.util.focus(h)
		}
	};
	f.plugin.fontsize = {
		click : function(h) {
			var a = [ "9px", "10px", "12px", "14px", "16px", "18px", "24px",
					"32px" ];
			f.util.selection(h);
			for ( var b = f.queryCommandValue(f.g[h].iframeDoc, "fontsize"), d = new f.menu(
					{
						id : h,
						cmd : "fontsize",
						width : 120
					}), e = 0, g = a.length; e < g; e++) {
				var i = a[e];
				d.add('<span class="ke-reset" style="font-size: ' + i + ';">'
						+ i + "</span>", function(j) {
					return function() {
						f.plugin.fontsize.exec(h, j)
					}
				}(i), {
					height : parseInt(i) + 12 + "px",
					checked : b === i
				})
			}
			d.show();
			this.menu = d
		},
		exec : function(h, a) {
			(new f.cmd(h)).wrap("span", [ {
				".font-size" : a
			} ]);
			f.util.execOnchangeHandler(h);
			this.menu.hide();
			f.util.focus(h)
		}
	};
	f.plugin.hr = {
		click : function(h) {
			f.util.selection(h);
			f.util.insertHtml(h, "<hr />");
			f.util.focus(h)
		}
	};
	f.plugin.print = {
		click : function(h) {
			f.util.selection(h);
			f.g[h].iframeWin.print()
		}
	};
	f.plugin.removeformat = {
		click : function(h) {
			f.util.selection(h);
			for ( var a = new f.cmd(h), b = {
				"*" : [ "class", "style" ]
			}, d = 0, e = f.g[h].inlineTags.length; d < e; d++)
				b[f.g[h].inlineTags[d]] = [ "*" ];
			a.remove(b);
			f.util.execOnchangeHandler(h);
			f.toolbar.updateState(h);
			f.util.focus(h)
		}
	};
	f.plugin.source = {
		click : function(h) {
			var a = f.g[h];
			if (a.wyswygMode) {
				f.hideMenu(h);
				a.newTextarea.value = f.util.getData(h);
				a.iframe.style.display = "none";
				a.newTextarea.style.display = "block";
				f.toolbar.disable(h, [ "source", "fullscreen" ]);
				a.wyswygMode = false;
				this.isSelected = true;
				f.toolbar.select(h, "source")
			} else {
				f.util.setFullHtml(h, a.newTextarea.value);
				a.iframe.style.display = "block";
				a.newTextarea.style.display = "none";
				f.toolbar.able(h, [ "source", "fullscreen" ]);
				a.wyswygMode = true;
				this.isSelected = false;
				f.toolbar.unselect(h, "source")
			}
			f.util.focus(h)
		}
	};
	f.plugin.textcolor = {
		click : function(h) {
			f.util.selection(h);
			var a = f.queryCommandValue(f.g[h].iframeDoc, "textcolor");
			this.menu = new f.menu( {
				id : h,
				cmd : "textcolor"
			});
			this.menu.picker(a)
		},
		exec : function(h, a) {
			var b = new f.cmd(h);
			a == "" ? b.remove( {
				span : [ ".color" ],
				font : [ "color" ]
			}) : b.wrap("span", [ {
				".color" : a
			} ]);
			f.util.execOnchangeHandler(h);
			this.menu.hide();
			f.util.focus(h)
		}
	};
	f.plugin.title = {
		click : function(h) {
			var a = f.lang.plugins.title;
			a = {
				H1 : a.h1,
				H2 : a.h2,
				H3 : a.h3,
				H4 : a.h4,
				P : a.p
			};
			var b = {
				H1 : 28,
				H2 : 24,
				H3 : 18,
				H4 : 14,
				P : 12
			};
			f.util.selection(h);
			var d = f.queryCommandValue(f.g[h].iframeDoc, "formatblock"), e = new f.menu(
					{
						id : h,
						cmd : "title",
						width : f.langType == "en" ? 200 : 150
					});
			f.each(a, function(g, i) {
				var j = "font-size:" + b[g] + "px;";
				if (g !== "P")
					j += "font-weight:bold;";
				e.add('<span class="ke-reset" style="' + j + '">' + i
						+ "</span>", function() {
					f.plugin.title.exec(h, "<" + g + ">")
				}, {
					height : b[g] + 12 + "px",
					checked : d === g.toLowerCase() || d === i.toLowerCase()
				})
			});
			e.show();
			this.menu = e
		},
		exec : function(h, a) {
			f.util.select(h);
			f.util.execCommand(h, "formatblock", a);
			this.menu.hide();
			f.util.focus(h)
		}
	};
	f.plugin.emoticons = {
		click : function(h) {
			function a(q) {
				var w = f.$$("table");
				if (n) {
					w.onmouseover = function() {
						n.style.display = "block"
					};
					w.onmouseout = function() {
						n.style.display = "none"
					}
				}
				w.className = "ke-plugin-emoticons-table";
				w.cellPadding = 0;
				w.cellSpacing = 0;
				w.border = 0;
				q = (q - 1) * j + i;
				for ( var u = 0; u < e; u++)
					for ( var x = w.insertRow(u), A = 0; A < g; A++) {
						var B = x.insertCell(A);
						B.className = "ke-plugin-emoticons-cell";
						B.onmouseover = n ? function(v, z) {
							return function() {
								if (v > l) {
									n.style.left = 0;
									n.style.right = ""
								} else {
									n.style.left = "";
									n.style.right = 0
								}
								r.src = p + z + ".gif";
								this.className = "ke-plugin-emoticons-cell ke-plugin-emoticons-cell-on"
							}
						}(A, q)
								: function() {
									this.className = "ke-plugin-emoticons-cell ke-plugin-emoticons-cell-on"
								};
						B.onmouseout = function() {
							this.className = "ke-plugin-emoticons-cell"
						};
						B.onclick = function(v) {
							return function() {
								d.exec(h, v);
								return false
							}
						}(q);
						var y = f.$$("span");
						y.className = "ke-plugin-emoticons-img";
						y.style.backgroundPosition = "-" + 24 * q + "px 0px";
						B.appendChild(y);
						q++
					}
				return w
			}
			function b(q) {
				for ( var w = 1; w <= k; w++) {
					if (q !== w) {
						var u = f.$$("a");
						u.href = "javascript:;";
						u.innerHTML = "[" + w + "]";
						u.onclick = function(x) {
							return function() {
								o.removeChild(s);
								var A = a(x);
								o.insertBefore(A, t);
								s = A;
								t.innerHTML = "";
								b(x);
								return false
							}
						}(w);
						t.appendChild(u)
					} else
						t.appendChild(document.createTextNode("[" + w + "]"));
					t.appendChild(document.createTextNode(" "))
				}
			}
			var d = this, e = 5, g = 9, i = 0, j = e * g, k = Math
					.ceil(135 / j), l = Math.floor(g / 2), m = f.g[h], p = m.pluginsPath
					+ "emoticons/";
			m = m.allowPreviewEmoticons === c ? true : m.allowPreviewEmoticons;
			f.util.selection(h);
			var o = f.$$("div");
			o.className = "ke-plugin-emoticons-wrapper";
			var n, r;
			if (m) {
				n = f.$$("div");
				n.className = "ke-plugin-emoticons-preview";
				n.style.right = 0;
				r = f.$$("img");
				r.className = "ke-reset";
				r.src = p + "0.gif";
				r.border = 0;
				n.appendChild(r);
				o.appendChild(n)
			}
			var s = a(1);
			o.appendChild(s);
			var t = f.$$("div");
			t.className = "ke-plugin-emoticons-page";
			o.appendChild(t);
			b(1);
			m = new f.menu( {
				id : h,
				cmd : "emoticons"
			});
			m.append(o);
			m.show();
			this.menu = m
		},
		exec : function(h, a) {
			var b = f.g[h].pluginsPath + "emoticons/" + a + ".gif";
			f.util.insertHtml(h, '<img src="' + b + '" kesrc="' + b
					+ '" border="0" alt="" />');
			this.menu.hide();
			f.util.focus(h)
		}
	};
	f.plugin.flash = {
		init : function(h) {
			f.g[h].getHtmlHooks
					.push(function(a) {
						return a
								.replace(
										/<img[^>]*class="?ke-flash"?[^>]*>/ig,
										function(b) {
											var d = b
													.match(/style="[^"]*;?\s*width:\s*(\d+)/i) ? RegExp.$1
													: 0, e = b
													.match(/style="[^"]*;?\s*height:\s*(\d+)/i) ? RegExp.$1
													: 0;
											d = d
													|| (b
															.match(/width="([^"]+)"/i) ? RegExp.$1
															: 0);
											e = e
													|| (b
															.match(/height="([^"]+)"/i) ? RegExp.$1
															: 0);
											if (b.match(/kesrctag="([^"]+)"/i)) {
												b = f.util
														.getAttrList(unescape(RegExp.$1));
												b.width = d || b.width || 0;
												b.height = e || b.height || 0;
												b.kesrc = b.src;
												return f.util.getMediaEmbed(b)
											}
										})
					});
			f.g[h].setHtmlHooks
					.push(function(a) {
						return a
								.replace(
										/<embed[^>]*type="application\/x-shockwave-flash"[^>]*>(?:<\/embed>)?/ig,
										function(b) {
											var d = b
													.match(/\s+src="([^"]+)"/i) ? RegExp.$1
													: "";
											if (b.match(/\s+kesrc="([^"]+)"/i))
												d = RegExp.$1;
											var e = b
													.match(/\s+width="([^"]+)"/i) ? RegExp.$1
													: 0, g = b
													.match(/\s+height="([^"]+)"/i) ? RegExp.$1
													: 0;
											b = f.util.getAttrList(b);
											b.src = d;
											b.width = e;
											b.height = g;
											return f.util.getMediaImage(h,
													"flash", b)
										})
					})
		},
		click : function(h) {
			f.util.selection(h);
			this.dialog = new f.dialog( {
				id : h,
				cmd : "flash",
				file : "flash.html",
				width : 400,
				height : 140,
				loadingMode : true,
				title : f.lang.flash,
				yesButton : f.lang.yes,
				noButton : f.lang.no
			});
			this.dialog.show()
		},
		check : function(h, a, b, d) {
			h = f.util.getIframeDoc(this.dialog.iframe);
			if (!a.match(/^.{3,}$/)) {
				alert(f.lang.invalidUrl);
				f.$("url", h).focus();
				return false
			}
			if (!b.match(/^\d*$/)) {
				alert(f.lang.invalidWidth);
				f.$("width", h).focus();
				return false
			}
			if (!d.match(/^\d*$/)) {
				alert(f.lang.invalidHeight);
				f.$("height", h).focus();
				return false
			}
			return true
		},
		exec : function(h) {
			var a = f.util.getIframeDoc(this.dialog.iframe), b = f.$("url", a).value, d = f
					.$("width", a).value;
			a = f.$("height", a).value;
			if (!this.check(h, b, d, a))
				return false;
			b = f.util.getMediaImage(h, "flash", {
				src : b,
				type : f.g[h].mediaTypes.flash,
				width : d,
				height : a,
				quality : "high"
			});
			f.util.insertHtml(h, b);
			this.dialog.hide();
			f.util.focus(h)
		}
	};
	f.plugin.image = {
		getSelectedNode : function(h) {
			h = f.g[h];
			var a = h.keRange.startNode, b = h.keRange.endNode;
			if (f.browser.WEBKIT || h.keSel.isControl)
				if (a.nodeType == 1)
					if (a.tagName.toLowerCase() == "img")
						if (a == b)
							if (!a.className.match(/^ke-\w+/i))
								return a
		},
		init : function(h) {
			var a = this;
			h = f.g[h];
			h.contextmenuItems
					.push( {
						text : f.lang.editImage,
						click : function(b, d) {
							f.util.select(b);
							d.hide();
							a.click(b)
						},
						cond : function(b) {
							return a.getSelectedNode(b)
						},
						options : {
							width : "150px",
							iconHtml : '<span class="ke-common-icon ke-common-icon-url ke-icon-image"></span>'
						}
					});
			h.contextmenuItems.push( {
				text : f.lang.deleteImage,
				click : function(b, d) {
					f.util.select(b);
					d.hide();
					var e = a.getSelectedNode(b);
					e.parentNode.removeChild(e);
					f.util.execOnchangeHandler(b)
				},
				cond : function(b) {
					return a.getSelectedNode(b)
				},
				options : {
					width : "150px"
				}
			});
			h.contextmenuItems.push("-")
		},
		click : function(h) {
			f.util.selection(h);
			this.dialog = new f.dialog( {
				id : h,
				cmd : "image",
				file : "image/image.html",
				width : 400,
				height : 220,
				loadingMode : true,
				title : f.lang.image,
				yesButton : f.lang.yes,
				noButton : f.lang.no
			});
			this.dialog.show()
		},
		check : function() {
			var h = f.util.getIframeDoc(this.dialog.iframe), a = f.$("type", h).value, b = f
					.$("imgWidth", h).value, d = f.$("imgHeight", h).value;
			f.$("imgTitle", h);
			a = a == 2 ? f.$("imgFile", h) : f.$("url", h);
			if (!a.value.match(/\.(jpg|jpeg|gif|bmp|png)(\s|\?|$)/i)) {
				alert(f.lang.invalidImg);
				a.focus();
				return false
			}
			if (!b.match(/^\d*$/)) {
				alert(f.lang.invalidWidth);
				f.$("imgWidth", h).focus();
				return false
			}
			if (!d.match(/^\d*$/)) {
				alert(f.lang.invalidHeight);
				f.$("imgHeight", h).focus();
				return false
			}
			return true
		},
		exec : function(h) {
			for ( var a = this, b = f.util.getIframeDoc(this.dialog.iframe), d = f
					.$("type", b).value, e = f.$("imgWidth", b).value, g = f.$(
					"imgHeight", b).value, i = f.$("imgTitle", b).value, j = b
					.getElementsByName("align"), k = "", l = 0, m = j.length; l < m; l++)
				if (j[l].checked) {
					k = j[l].value;
					break
				}
			if (!this.check(h))
				return false;
			if (d == 2) {
				f.$("editorId", b).value = h;
				var p = f.$("uploadIframe", b);
				f.util.showLoadingPage(h);
				var o = function() {
					f.event.remove(p, "load", o);
					f.util.hideLoadingPage(h);
					var n = f.util.getIframeDoc(p), r = "";
					try {
						r = f.util.parseJson(n.body.innerHTML)
					} catch (s) {
						alert(f.lang.invalidJson)
					}
					if (typeof r === "object" && "error" in r)
						if (r.error === 0) {
							n = f.format.getUrl(r.url, "absolute");
							a.insert(h, n, i, e, g, 0, k)
						} else {
							alert(r.message);
							return false
						}
				};
				f.event.add(p, "load", o);
				b.uploadForm.submit()
			} else {
				b = f.$("url", b).value;
				this.insert(h, b, i, e, g, 0, k)
			}
		},
		insert : function(h, a, b, d, e, g, i) {
			a = '<img src="' + a + '" kesrc="' + a + '" ';
			if (d > 0)
				a += 'width="' + d + '" ';
			if (e > 0)
				a += 'height="' + e + '" ';
			if (b)
				a += 'title="' + b + '" ';
			if (i)
				a += 'align="' + i + '" ';
			a += 'alt="' + b + '" ';
			a += 'border="' + g + '" />';
			f.util.insertHtml(h, a);
			this.dialog.hide();
			f.util.focus(h)
		}
	};
	f.plugin.link = {
		getSelectedNode : function(h) {
			return f.getCommonAncestor(f.g[h].keSel, "a")
		},
		init : function(h) {
			var a = this;
			f.g[h].contextmenuItems
					.push( {
						text : f.lang.editLink,
						click : function(b, d) {
							f.util.select(b);
							d.hide();
							a.click(b)
						},
						cond : function(b) {
							return a.getSelectedNode(b)
						},
						options : {
							width : "150px",
							iconHtml : '<span class="ke-common-icon ke-common-icon-url ke-icon-link"></span>'
						}
					})
		},
		click : function(h) {
			f.util.selection(h);
			this.dialog = new f.dialog( {
				id : h,
				cmd : "link",
				file : "link/link.html",
				width : 400,
				height : 90,
				loadingMode : true,
				title : f.lang.link,
				yesButton : f.lang.yes,
				noButton : f.lang.no
			});
			this.dialog.show()
		},
		exec : function(h) {
			var a = f.g[h];
			f.util.select(h);
			var b = a.keRange, d = b.startNode, e = b.endNode, g = a.iframeDoc, i = f.util
					.getIframeDoc(this.dialog.iframe), j = f.$("hyperLink", i).value, k = f
					.$("linkType", i).value;
			if (!j.match(/.+/) || j.match(/^\w+:\/\/\/?$/)) {
				alert(f.lang.invalidUrl);
				f.$("hyperLink", i).focus();
				return false
			}
			for (i = b.getParentElement(); i;) {
				if (i.tagName.toLowerCase() == "a"
						|| i.tagName.toLowerCase() == "body")
					break;
				i = i.parentNode
			}
			i = i.parentNode;
			e = f.browser.IE ? !!a.range.item : d.nodeType == 1 && d === e
					&& d.nodeName.toLowerCase() != "br";
			var l = !e;
			e
					|| (l = f.browser.IE ? a.range.text === "" : a.range
							.toString() === "");
			if (l || f.util.isEmpty(h)) {
				a = '<a href="' + j + '"';
				if (k)
					a += ' target="' + k + '"';
				a += ">" + j + "</a>";
				f.util.insertHtml(h, a)
			} else {
				g.execCommand("createlink", false, "__ke_temp_url__");
				i = i.getElementsByTagName("a");
				l = 0;
				for ( var m = i.length; l < m; l++)
					if (i[l].href.match(/\/?__ke_temp_url__$/)) {
						i[l].href = j;
						i[l].setAttribute("kesrc", j);
						if (k)
							i[l].target = k;
						else
							i[l].removeAttribute("target")
					}
				if (f.browser.WEBKIT && e && d.tagName.toLowerCase() == "img") {
					e = d.parentNode;
					if (e.tagName.toLowerCase() != "a") {
						g = f.$$("a", g);
						e.insertBefore(g, d);
						g.appendChild(d);
						e = g
					}
					e.href = j;
					e.setAttribute("kesrc", j);
					if (k)
						e.target = k;
					else
						e.removeAttribute("target");
					a.keSel.addRange(b)
				}
			}
			f.util.execOnchangeHandler(h);
			this.dialog.hide();
			f.util.focus(h)
		}
	};
	f.plugin.unlink = {
		init : function(h) {
			var a = this;
			f.g[h].contextmenuItems
					.push( {
						text : f.lang.deleteLink,
						click : function(b, d) {
							f.util.select(b);
							d.hide();
							a.click(b)
						},
						cond : function(b) {
							return f.plugin.link.getSelectedNode(b)
						},
						options : {
							width : "150px",
							iconHtml : '<span class="ke-common-icon ke-common-icon-url ke-icon-unlink"></span>'
						}
					});
			f.g[h].contextmenuItems.push("-")
		},
		click : function(h) {
			var a = f.g[h], b = a.iframeDoc;
			f.util.selection(h);
			var d = a.keRange, e = d.startNode;
			d = d.endNode;
			d = e.nodeType == 1 && e === d;
			var g = !d;
			d
					|| (g = f.browser.IE ? a.range.text === "" : a.range
							.toString() === "");
			if (g) {
				g = f.plugin.link.getSelectedNode(h);
				if (!g)
					return;
				d = a.keRange;
				d.selectTextNode(g);
				a.keSel.addRange(d);
				f.util.select(h);
				b.execCommand("unlink", false, null);
				if (f.browser.WEBKIT && e.tagName.toLowerCase() == "img") {
					b = e.parentNode;
					if (b.tagName.toLowerCase() == "a") {
						f.util.removeParent(b);
						a.keSel.addRange(d)
					}
				}
			} else
				b.execCommand("unlink", false, null);
			f.util.execOnchangeHandler(h);
			f.toolbar.updateState(h);
			f.util.focus(h)
		}
	};
	f.plugin.media = {
		init : function(h) {
			var a = {};
			f.each(f.g[h].mediaTypes, function(b, d) {
				a[d] = b
			});
			f.g[h].getHtmlHooks
					.push(function(b) {
						return b
								.replace(
										/<img[^>]*class="?ke-\w+"?[^>]*>/ig,
										function(d) {
											var e = d
													.match(/style="[^"]*;?\s*width:\s*(\d+)/i) ? RegExp.$1
													: 0, g = d
													.match(/style="[^"]*;?\s*height:\s*(\d+)/i) ? RegExp.$1
													: 0;
											e = e
													|| (d
															.match(/width="([^"]+)"/i) ? RegExp.$1
															: 0);
											g = g
													|| (d
															.match(/height="([^"]+)"/i) ? RegExp.$1
															: 0);
											if (d
													.match(/\s+kesrctag="([^"]+)"/i)) {
												d = f.util
														.getAttrList(unescape(RegExp.$1));
												d.width = e || d.width || 0;
												d.height = g || d.height || 0;
												d.kesrc = d.src;
												return f.util.getMediaEmbed(d)
											}
										})
					});
			f.g[h].setHtmlHooks
					.push(function(b) {
						return b
								.replace(
										/<embed[^>]*type="([^"]+)"[^>]*>(?:<\/embed>)?/ig,
										function(d, e) {
											if (typeof a[e] == "undefined")
												return d;
											var g = d
													.match(/\s+src="([^"]+)"/i) ? RegExp.$1
													: "";
											if (d.match(/\s+kesrc="([^"]+)"/i))
												g = RegExp.$1;
											var i = d
													.match(/\s+width="([^"]+)"/i) ? RegExp.$1
													: 0, j = d
													.match(/\s+height="([^"]+)"/i) ? RegExp.$1
													: 0, k = f.util
													.getAttrList(d);
											k.src = g;
											k.width = i;
											k.height = j;
											return f.util.getMediaImage(h, "",
													k)
										})
					})
		},
		click : function(h) {
			f.util.selection(h);
			this.dialog = new f.dialog( {
				id : h,
				cmd : "media",
				file : "media.html",
				width : 400,
				height : 170,
				loadingMode : true,
				title : f.lang.media,
				yesButton : f.lang.yes,
				noButton : f.lang.no
			});
			this.dialog.show()
		},
		check : function(h, a, b, d) {
			h = f.util.getIframeDoc(this.dialog.iframe);
			if (!a
					.match(/^.{3,}\.(swf|flv|mp3|wav|wma|wmv|mid|avi|mpg|mpeg|asf|rm|rmvb)(\?|$)/i)) {
				alert(f.lang.invalidMedia);
				f.$("url", h).focus();
				return false
			}
			if (!b.match(/^\d*$/)) {
				alert(f.lang.invalidWidth);
				f.$("width", h).focus();
				return false
			}
			if (!d.match(/^\d*$/)) {
				alert(f.lang.invalidHeight);
				f.$("height", h).focus();
				return false
			}
			return true
		},
		exec : function(h) {
			var a = f.util.getIframeDoc(this.dialog.iframe), b = f.$("url", a).value, d = f
					.$("width", a).value, e = f.$("height", a).value;
			if (!this.check(h, b, d, e))
				return false;
			a = f.$("autostart", a).checked ? "true" : "false";
			b = f.util.getMediaImage(h, "", {
				src : b,
				type : f.g[h].mediaTypes[f.util.getMediaType(b)],
				width : d,
				height : e,
				autostart : a,
				loop : "true"
			});
			f.util.insertHtml(h, b);
			this.dialog.hide();
			f.util.focus(h)
		}
	};
	f.plugin.advtable = {
		getSelectedTable : function(h) {
			return f.getCommonAncestor(f.g[h].keSel, "table")
		},
		getSelectedRow : function(h) {
			return f.getCommonAncestor(f.g[h].keSel, "tr")
		},
		getSelectedCell : function(h) {
			return f.getCommonAncestor(f.g[h].keSel, "td")
		},
		tableprop : function(h) {
			this.click(h)
		},
		tableinsert : function(h) {
			this.click(h, "insert")
		},
		tabledelete : function(h) {
			h = this.getSelectedTable(h);
			h.parentNode.removeChild(h)
		},
		tablecolinsert : function(h, a) {
			for ( var b = this.getSelectedTable(h), d = this.getSelectedCell(h).cellIndex
					+ a, e = 0, g = b.rows.length; e < g; e++)
				b.rows[e].insertCell(d).innerHTML = "&nbsp;"
		},
		tablecolinsertleft : function(h) {
			this.tablecolinsert(h, 0)
		},
		tablecolinsertright : function(h) {
			this.tablecolinsert(h, 1)
		},
		tablerowinsert : function(h, a) {
			var b = this.getSelectedTable(h), d = this.getSelectedRow(h);
			b = b.insertRow(d.rowIndex + a);
			var e = 0;
			for (d = d.cells.length; e < d; e++)
				b.insertCell(e).innerHTML = "&nbsp;"
		},
		tablerowinsertabove : function(h) {
			this.tablerowinsert(h, 0)
		},
		tablerowinsertbelow : function(h) {
			this.tablerowinsert(h, 1)
		},
		tablecoldelete : function(h) {
			var a = this.getSelectedTable(h);
			h = this.getSelectedCell(h).cellIndex;
			for ( var b = 0, d = a.rows.length; b < d; b++)
				a.rows[b].deleteCell(h)
		},
		tablerowdelete : function(h) {
			var a = this.getSelectedTable(h);
			h = this.getSelectedRow(h);
			a.deleteRow(h.rowIndex)
		},
		init : function(h) {
			for ( var a = this, b = "prop,colinsertleft,colinsertright,rowinsertabove,rowinsertbelow,coldelete,rowdelete,insert,delete"
					.split(","), d = 0, e = b.length; d < e; d++) {
				var g = "table" + b[d];
				f.g[h].contextmenuItems
						.push( {
							text : f.lang[g],
							click : function(i) {
								return function(j, k) {
									f.util.select(j);
									k.hide();
									a[i] !== c && a[i](j);
									/prop/.test(i)
											|| f.util.execOnchangeHandler(j)
								}
							}(g),
							cond : function(i) {
								return f.util.inArray(i, [ "tableprop",
										"tabledelete" ]) ? function(j) {
									return a.getSelectedTable(j)
								} : function(j) {
									return a.getSelectedCell(j)
								}
							}(g),
							options : {
								width : "170px",
								iconHtml : '<span class="ke-common-icon ke-common-icon-url ke-icon-' + g + '"></span>'
							}
						})
			}
			f.g[h].contextmenuItems.push("-");
			f.g[h].setHtmlHooks.push(function(i) {
				return i.replace(/<table([^>]*)>/ig, function(j, k) {
					if (k.match(/\s+border=["']?(\d*)["']?/ig)) {
						var l = RegExp.$1;
						return k.indexOf("ke-zeroborder") < 0
								&& (l === "" || l === "0") ? f.addClass(j,
								"ke-zeroborder") : j
					} else
						return f.addClass(j, "ke-zeroborder")
				})
			})
		},
		click : function(h, a) {
			a = a || "default";
			f.util.selection(h);
			this.dialog = new f.dialog( {
				id : h,
				cmd : "advtable",
				file : "advtable/advtable.html?mode=" + a,
				width : 420,
				height : 220,
				loadingMode : true,
				title : f.lang.advtable,
				yesButton : f.lang.yes,
				noButton : f.lang.no
			});
			this.dialog.show()
		},
		exec : function(h) {
			var a = f.util.getIframeDoc(this.dialog.iframe), b = f.$("mode", a), d = f
					.$("rows", a), e = f.$("cols", a), g = f.$("width", a), i = f
					.$("height", a), j = f.$("widthType", a), k = f.$(
					"heightType", a), l = f.$("padding", a), m = f.$("spacing",
					a), p = f.$("align", a), o = f.$("border", a), n = f.$(
					"borderColor", a), r = f.$("backgroundColor", a);
			a = d.value;
			var s = e.value, t = g.value, q = i.value;
			j = j.value;
			var w = k.value;
			k = l.value;
			var u = m.value;
			p = p.value;
			var x = o.value;
			n = n.innerHTML;
			r = r.innerHTML;
			if (a == "" || a == 0 || !a.match(/^\d*$/)) {
				alert(f.lang.invalidRows);
				d.focus();
				return false
			}
			if (s == "" || s == 0 || !s.match(/^\d*$/)) {
				alert(f.lang.invalidCols);
				e.focus();
				return false
			}
			if (!t.match(/^\d*$/)) {
				alert(f.lang.invalidWidth);
				g.focus();
				return false
			}
			if (!q.match(/^\d*$/)) {
				alert(f.lang.invalidHeight);
				i.focus();
				return false
			}
			if (!k.match(/^\d*$/)) {
				alert(f.lang.invalidPadding);
				l.focus();
				return false
			}
			if (!u.match(/^\d*$/)) {
				alert(f.lang.invalidSpacing);
				m.focus();
				return false
			}
			if (!x.match(/^\d*$/)) {
				alert(f.lang.invalidBorder);
				o.focus();
				return false
			}
			if (b.value === "update") {
				a = this.getSelectedTable(h);
				if (t !== "")
					a.style.width = t + j;
				else if (a.style.width)
					a.style.width = "";
				a.width !== c && a.removeAttribute("width");
				if (q !== "")
					a.style.height = q + w;
				else if (a.style.height)
					a.style.height = "";
				a.height !== c && a.removeAttribute("height");
				if (r !== "")
					a.style.backgroundColor = r;
				else if (a.style.backgroundColor)
					a.style.backgroundColor = "";
				a.bgColor !== c && a.removeAttribute("bgColor");
				if (k !== "")
					a.cellPadding = k;
				else
					a.removeAttribute("cellPadding");
				if (u !== "")
					a.cellSpacing = u;
				else
					a.removeAttribute("cellSpacing");
				if (p !== "")
					a.align = p;
				else
					a.removeAttribute("align");
				x === "" || x === "0" ? f.addClass(a, "ke-zeroborder") : f
						.removeClass(a, "ke-zeroborder");
				x !== "" ? a.setAttribute("border", x) : a
						.removeAttribute("border");
				n !== "" ? a.setAttribute("borderColor", n) : a
						.removeAttribute("borderColor");
				f.util.execOnchangeHandler(h)
			} else {
				b = "";
				if (t !== "")
					b += "width:" + t + j + ";";
				if (q !== "")
					b += "height:" + q + w + ";";
				if (r !== "")
					b += "background-color:" + r + ";";
				t = "<table";
				if (b !== "")
					t += ' style="' + b + '"';
				if (k !== "")
					t += ' cellpadding="' + k + '"';
				if (u !== "")
					t += ' cellspacing="' + u + '"';
				if (p !== "")
					t += ' align="' + p + '"';
				if (x === "" || x === "0")
					t += ' class="ke-zeroborder"';
				if (x !== "")
					t += ' border="' + x + '"';
				if (n !== "")
					t += ' bordercolor="' + n + '"';
				t += ">";
				for (q = 0; q < a; q++) {
					t += "<tr>";
					for (b = 0; b < s; b++)
						t += "<td>&nbsp;</td>";
					t += "</tr>"
				}
				t += "</table>";
				f.util.insertHtml(h, t)
			}
			this.dialog.hide();
			f.util.focus(h)
		}
	}
})(KindEditor);
