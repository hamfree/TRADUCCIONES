/* 
 * Funciones de navegación y de muestra de código fuente para los
 * Tutoriales de Java.
 */
function leftBar() {
    var nameq = 'tutorial_showLeftBar=';
    var cookies = document.cookie.split(';');
    for (var i = 0; i < cookies.length; i++) {
        var cookieString = cookies[i];
        while (cookieString.charAt(0) === ' ') {
            cookieString = cookieString.substring(1, cookieString.length);
        }
        if (cookieString.indexOf(nameq) === 0) {
            cookieValue = cookieString.substring(nameq.length,
                    cookieString.length);
            return cookieValue === 'yes';
        }
    }
    return true;
}

function showLeft(b) {
    var contents = document.getElementById("LeftBar");
    var main = document.getElementById("MainFlow");
    var toggle = document.getElementById("ToggleLeft");
    if (b) {
        contents.className = "LeftBar_shown";
        main.className = "MainFlow_indented";
        toggle.innerHTML = "Ocultar TdC";
        document.cookie = 'tutorial_showLeftBar=yes; path=/; samesite=lax;';
    } else {
        contents.className = "LeftBar_hidden";
        main.className = "MainFlow_wide";
        toggle.innerHTML = "Mostrar TdC";
        document.cookie = 'tutorial_showLeftBar=no; path=/; samesite=lax;';
    }
}

function toggleLeft() {
    showLeft(document.getElementById("LeftBar").className === "LeftBar_hidden");
    document.getElementById("ToggleLeft").blur();
}

function load() {
    showLeft(leftBar());
    document.getElementById("ToggleLeft").style.display = "inline";
}

function showCode(displayCodePage, codePath) {
    var codePathEls = codePath.split("/");
    var currDocPathEls = location.href.split("/");
    currDocPathEls.pop();
    while (codePathEls.length > 0) {
        if (codePathEls[0] === "..") {
            codePathEls.shift();
            currDocPathEls.pop();
        } else {
            break;
        }
    }
    var fullCodePath = currDocPathEls.join("/") + "/" + codePathEls.join("/");
    if (codePath.indexOf(".java") !== -1 || codePath.indexOf(".jnlp") !== -1) {
        window.location.href = displayCodePage + "?code=" + encodeURI(fullCodePath);
    } else {
        window.location.href = fullCodePath;
    }
}