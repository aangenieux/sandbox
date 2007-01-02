dojo.provide("dojo.widget.DayCalendar");

dojo.require("dojo.html.*");
dojo.require("dojo.widget.*");
dojo.require("dojo.collections.ArrayList");
dojo.require("dojo.json");


dojo.widget.defineWidget (
	"dojo.widget.DayCalendar",
	dojo.widget.HtmlWidget,
	{

	templatePath: dojo.uri.dojoUri("../DayCalendar.htm"),
	templateCssPath: dojo.uri.dojoUri("../DayCalendar.css"),
	
	//first selected div in day
	firstSelected: null,
	
	//last selected div in day
	lastSelected: null,
	
	//div  : current slice time
	current: null,
	
	//div : containing text;
	timeDiv: null,
	
	//div : reziseHandler
	resizeDiv: null,
	
	//is the mouse down?
	mousedown: false,
	
	//list of slice time
	timeRange: null,
	
	//incremented on each new Slice
	slicePartId: 0,
	
	//Selected Slice
	selectedTimeRange: null,
	
	//if a weekCalendar exsit add it here
	weekCalendar : null,
	
	tmpx: null,
	
	postCreate: function(args, fragment, parent){
		this.timeRange =  new dojo.collections.ArrayList();
		var body = document.getElementsByTagName("body")[0];
		dojo.event.connect(body, "onmouseup", this, "fixTimeRange");
		dojo.event.connect(document, "onkeydown", this, "keyPressed");
	},
	
	/**
	 * Event Listener
	 */ 
	mouseOverDiv: function(event){
		div = event.target;
		div.innerHTML = div.getAttribute('start') + ' - ' + div.getAttribute('end');
		this.expandTimeRange(div);
	},
	
	mouseOutDiv: function(event){
		div = event.target;
		div.innerHTML='';
	},
	
	mouseDownDiv: function(event){
		div = event.target;
		this.createTimeRange(div);
	},
	
	mouseMoveRange: function(event){
		div = event.target;
		this.reduceTimeRange(event)
	},
	
	clickRange: function(event){
		div = event.target;
		if (div.getElementsByTagName('div').length == 0){
			div = div.parentNode;
		}
		this.selectTimeRange(div);
	},
	
	clickHandler: function(event){
		//if a div id selected disable it
		if (this.selectedTimeRange != null){
			//remove selected Style
			with (this.selectedTimeRange.style){
				backgroundColor = "#4444FF";
			}
			this.selectedTimeRange = null;
		}
		this.resizeDiv = event.target;
		this.current = event.target.parentNode;
		this.timeDiv = this.current.getElementsByTagName("div")[0];
		
		//send event to delete this range server side
		var val = this.current.getElementsByTagName('div')[0].innerHTML;
		var start = val.substring(0,5);
		var end = val.substring(8,val.length);
		this.onRemove(start, end, this.widgetId);
		if (this.weekCalendar != null){
			this.weekCalendar.onRemove(start, end, this.widgetId);
		}
		
		//and give hand to the others events...
		this.mousedown = true;
		var pos = dojo.html.toCoordinateObject(this.domNode,true);
		var posy = dojo.html.toCoordinateObject(this.current, true);
		var y = posy.top - pos.top;
		var divPos = Math.floor(y/10);
		this.firstSelected = this.domNode.getElementsByTagName("div")[divPos];
	},
	/**
	 * End Event Listener
	 */
	
	
	createTimeRange: function(div){
		//TODO : try to do that in the template to optimize calendar loading
		var pos = dojo.html.toCoordinateObject(div,true);
		var domNodePos = dojo.html.toCoordinateObject(this.domNode,true);
		this.current = document.createElement("div");
		this.timeDiv = document.createElement("div");
		this.resizeDiv = document.createElement("div");
		with (this.current.style){
			borderStyle = "solid";
			borderColor = "#0000FF";
			backgroundColor = "#AAAAFF";
			borderWidth = "1px";
			height = pos.height - 3 +"px";
			width = pos.width - 3 + "px";
			position = "absolute";
			top = pos.top - domNodePos.top + "px";
			left = pos.left - domNodePos.left + "px";
			zIndex = "2"; 
		}
		
		//Time Div
		with (this.timeDiv.style){
			fontSize = "9px";
			textAlign = "center";
			fontWeight = "bold";
			marginTop = "-3px";
		}
		this.current.appendChild(this.timeDiv);
		
		//resize Div
		with (this.resizeDiv.style){
			zIndex = "3";
			position = "absolute";
			textAlign = "center";
			width = "100%";
		}
		this.current.appendChild(this.resizeDiv);
		this.resizeDiv.innerHTML = "=";
		dojo.event.connect(this.resizeDiv, "onmousedown", this, "clickHandler");
		
		this.domNode.appendChild(this.current);
		dojo.event.connect(this.current, "onmousemove", this, "mouseMoveRange");
		var id = this.widgetId + "_timeSlice_" + this.slicePartId++;
		this.current.setAttribute("id", id);
		dojo.event.connect(this.current, "onclick", this, "clickRange");
		dojo.html.setOpacity(this.current, 0.8, false);
		this.firstSelected = div;
		this.mousedown = true;
		this.expandTimeRange(this.firstSelected);
	},

	getDivUnderMouse: function(cursor){
		var pos = dojo.html.toCoordinateObject(this.domNode,true);
		var y = cursor.y - pos.top;
		
		var divPos = Math.floor(y/10);

		return this.domNode.getElementsByTagName("div")[divPos];
	},

	reduceTimeRange: function(e){
		if (this.mousedown){
			var cursor = dojo.html.getCursorPosition(e);
			this.lastSelected = this.getDivUnderMouse(cursor);
			if (this.lastSelected != null){
				this.doExpend();
			}else{
				//no div under cursor
			}
			
		}
	},

	expandTimeRange: function(div){
		if (this.mousedown){
			div.innerHTML='';
			this.lastSelected = div;
			this.doExpend();
		}
	},

	fixTimeRange: function (){
		if (this.mousedown){
			this.timeRange.add(this.current);
			this.firstSelected = null;
			this.lastSelected = null;
			with (this.current.style){
				backgroundColor = "#4444FF";
			}
			var val = this.current.getElementsByTagName('div')[0].innerHTML;
			var start = val.substring(0,5);
			var end = val.substring(8,val.length);
			this.onCreate(start, end, this.widgetId);
			if (this.weekCalendar != null){
				this.weekCalendar.onCreate(start, end, this.widgetId);
			}
			this.current = null;
			this.mousedown = false;
			this.tmpx = null;
			
		}
	},
	
	onCreate: function(start, end, widgetId){
	},

	doExpend: function (){
		if (this.firstSelected !=null){
			var newTop;
			var newLeft;
			var newHeigth;
			var newWidth;
			var firstPos = dojo.html.toCoordinateObject(this.firstSelected,true);
			var lastPos = dojo.html.toCoordinateObject(this.lastSelected,true);
			
			if(this.firstSelected.getAttribute("pos") < this.lastSelected.getAttribute("pos")){
				newTop = firstPos.top;
				newHeight = lastPos.top - firstPos.top + lastPos.height;
			}else{
				newTop = lastPos.top;
				newHeight = firstPos.top - lastPos.top + firstPos.height;
			}
			
			var domNodePos = dojo.html.toCoordinateObject(this.domNode,true);
			
			with (this.current.style){
				borderStyle = "solid";
				borderColor = "#0000FF";
				borderWidth = "1px";
				height = newHeight -3 +"px";
				width = firstPos.width - 3 + "px";
				position = "absolute";
				top = newTop - domNodePos.top + "px";
				left = firstPos.left - domNodePos.left + "px"; 
			}
			this.createTimeDiv();
			this.createResizeDiv(newHeight);
		}
	},
	
	createTime: function(){
		var firstPos = this.firstSelected.getAttribute("pos");
		var lastPos = this.lastSelected.getAttribute("pos");
		
		var time;
		
		if(firstPos < lastPos){
			time = this.firstSelected.getAttribute("start") + " - " + this.lastSelected.getAttribute("end");
		}else{
			time = this.lastSelected.getAttribute("start") + " - " + this.firstSelected.getAttribute("end");
		}
		return time;
	},
	
	createResizeDiv: function(newHeight){
		with (this.resizeDiv.style){
			top = (newHeight - 13) + "px";
		}
	},

	createTimeDiv: function(){
		this.timeDiv.innerHTML = this.createTime();
	},
	
	removeTimeRange: function(div){
		var val = div.getElementsByTagName('div')[0].innerHTML;
		var start = val.substring(0,5);
		var end = val.substring(8,val.length);
		this.onRemove(start, end, this.widgetId);
		if (this.weekCalendar != null){
			this.weekCalendar.onRemove(start, end, this.widgetId);
		}
		this.timeRange.remove(div);
		this.domNode.removeChild(div);
		
	},
	
	onRemove: function(start, end, widgetId){
	},
	
	selectTimeRange: function(div){
		if (this.selectedTimeRange != null && div == this.selectedTimeRange){
			//remove selected Style
			with (this.selectedTimeRange.style){
				backgroundColor = "#4444FF";
			}
			this.selectedTimeRange = null;
		} else {
			if (this.selectedTimeRange != null ){
				with (this.selectedTimeRange.style){
					backgroundColor = "#4444FF";
				}
			}
			this.selectedTimeRange = div;
			if (div != null){
				with (div.style){
					backgroundColor = "#FF44FF";
				}
				this.onSelect(div);
			}
		}
	},
	
	/**
	 * Listener on sliceTime selection
	 */
	onSelect: function(div){},
	
	keyPressed: function(e){
		if (e.keyCode == 46 && this.selectedTimeRange != null){  //DEL
			this.removeTimeRange(this.selectedTimeRange);
			this.selectedTimeRange = null;
		}
	},
	
	// ------------------------------------------------------------------ //
	//                          MODEL                                     //
	
	/**
	 * return the json object of the Day
	 */
	getValue: function(){
		var toReturn = new Array();
		this.timeRange.forEach(function(item){
			var toAdd = {};
			var val = item.getElementsByTagName('div')[0].innerHTML;
			toAdd.start = val.substring(0,5);
			toAdd.end = val.substring(8,val.length);
			toReturn.push(toAdd);
		});
		return toReturn;
	},
	
	addTimeRange: function(start, end){
		//translate end 00:00 to 24:00
		if (end == "00:00"){end = "24:00";}
		var inners = this.domNode.getElementsByTagName("div");
		for(var i=0; i<inners.length; i++){
			if (inners[i].getAttribute("start") == start){
				this.createTimeRange(inners[i]);
			}
			if (inners[i].getAttribute("end") == end){
				this.expandTimeRange(inners[i]);
				if (this.mousedown){
					this.timeRange.add(this.current);
					this.firstSelected = null;
					this.lastSelected = null;
					with (this.current.style){
						backgroundColor = "#4444FF";
					}
					this.current = null;
					this.mousedown = false;
					this.tmpx = null;
				}
			}
		}
	},
	
	setValue: function(obj){
		for(var i=0; i < obj.length; i++){
			this.addTimeRange(obj[i].start, obj[i].end);
		}
	}
});