function handleEnter (field, event) {  
  var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;  
    if (keyCode == 13) {  
      var i;  
      for (i = 0; i < field.form.elements.length; i++)  
        if (field == field.form.elements[i])  
          break;  
      i = (i + 1) % field.form.elements.length;  
      while (field.form.elements[i].type == "hidden")  
        i = (i + 1) % field.form.elements.length;  
      field.form.elements[i].focus();  
      return false;  
    }   
    else  
      return true;  
}       
     
function pular(){  
  if (window.event.srcElement.type != 'submit' && window.event.srcElement.type != 'button' && window.event.srcElement.type != 'reset' && window.event.srcElement.type != 'textarea'){  
    var ele = window.event.srcElement;  
    var index = ele.sourceIndex;  
    return handleEnter(ele, event);  
  }  
}  
     