function mostrarPopUpOrden() {
   PF('popUpOrdenar').show();
}

function ocultaPopUpOrden() {
   PF('popUpOrdenar').hide();
}

function mostrarPopUp() {
    PF('popUp').show();
 }

function ocultaPopUp() {
   if (validateErrorMessageIsMine()) {
      if (!hasMessages() || (getMessagesId() !== "j_idt12:j_idt20:no-keyword-created")) {
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
   if (validateErrorMessageIsMine()) {
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
   if ($errorMessages.length > 1) {
      return false;
   }
   return true;
}

function cambiarColorLike(isLiked) {
   
   if (isLiked === "true") {
      var colorToChange = "linear-gradient(to bottom, gray, gray)";
   } else {
      var colorToChange = "linear-gradient(to bottom, #990000, #990000)";
   }

   var likeButton = document.getElementById("like-button");
   likeButton.style.backgroundImage = colorToChange;
}

function cambiarColorLikeInverse () {
   var isLiked = document.getElementById("currentIdeaLike").value;
   
   if (isLiked !== "true") {
      var colorToChange = "linear-gradient(to bottom, gray, gray)";
   } else {
      var colorToChange = "linear-gradient(to bottom, #990000, #990000)";
   }
   
   var likeButton = document.getElementById("like-button");
   likeButton.style.backgroundImage = colorToChange;
}

$(document).ready(function() {
   if (window.location.href.indexOf("?nocache") > -1) {
      var buttonLike = document.getElementById("like-button");
      cambiarColorLikeInverse();
   }
});