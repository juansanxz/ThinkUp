function mostrarPopUpOrden() {
   PF('popUpOrdenar').show();
}

function ocultaPopUpOrden() {
   PF('popUpOrdenar').hide();
}

function mostrarPopUp() {
   PF('popUp').show();
}

function showComments() {
   PF('comments').show();
}

function ocultaPopUp() {
   //console.log(getMessagesId());
   if (validateErrorMessageIsMine()) {
      //console.log("ENtre");
      if (!hasMessages() || (getMessagesId() !== "j_idt12:j_idt20:no-keyword-created")) {
         //PF('popUp').hide();
         borrarInput();
      }
   }
}

function mostrarPopUpStatus() {
   PF('popUpEditarStatus').show();
}

function ocultaPopUpStatus() {
   PF('popUpEditarStatus').hide();
}

function mostrarPopUpIdea() {
   //console.log(getMessagesId());
   if (validateErrorMessageIsMine()) {
      //console.log("ENtre");
      if (!hasMessages() || (getMessagesId() !== "no-idea-created")) {
         PF('popUpIdea').show();
         borrarInfoIdea();
      }
   }
}

function borrarInfoIdea() {
   var inputs = document.getElementsByTagName("input");
   for (var i = 0; i < inputs.length; i++) {
      if (inputs[i].type === "text") {
         inputs[i].value = "";
      }
   }
}

function borrarInput() {
   var inputs = document.getElementsByTagName("input");
      for (var i = 0; i < inputs.length; i++) {
         if (inputs[i].type === "text" && inputs[i].id.indexOf("valorPopUp") !== -1) {
            inputs[i].value = "";
         }
      }
}

function hasMessages() {
   return !!$('.ui-messages-error').length;
}

function getMessagesId() {
   var $errorMessages = $('.ui-messages-error');
   if ($errorMessages.length) {
      return $errorMessages.first().parent().attr('id');
   }
   return null;
}

function validateErrorMessageIsMine() {
   var $errorMessages = $('.ui-messages-error');
   console.log($errorMessages.length);
   if ($errorMessages.length > 1) {
      return false;
   }
   return true;
}