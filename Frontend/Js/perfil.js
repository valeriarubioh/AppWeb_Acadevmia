const editProfileButton = document.getElementById("edit-profile-button");
const saveProfileButton = document.getElementById("save-profile-button");
const profilePictureInput = document.getElementById("profile-picture-input");
const addSocialWebsiteButton = document.getElementById("add-social-website-button");

// Agrega una variable para rastrear si estás en modo de edición
let inEditMode = false;

editProfileButton.addEventListener("click", function() {
    enableEditFields(true);
    showProfilePictureInput();

    selectValue.style.display = "block";
    skillsDropdown.style.display = "block";

    editProfileButton.style.display = "none";
    saveProfileButton.style.display = "block";

    // Muestra el botón de agregar red social al editar el perfil
    addSocialWebsiteButton.style.display = "block";

    // Muestra las "X" solo en modo de edición
    if (!inEditMode) {
        inEditMode = true;
        const removeButtons = document.querySelectorAll(".remove-button");
        removeButtons.forEach(function(button) {
            button.style.display = "block";
        });
    }

    // Resto del código de "Editar Perfil"
});

saveProfileButton.addEventListener("click", function() {
    enableEditFields(false);
    hideProfilePictureInput();

    selectValue.style.display = "none";
    skillsDropdown.style.display = "none";

    saveProfileButton.style.display = "none";
    editProfileButton.style.display = "block";

    // Oculta el botón de agregar red social al guardar los cambios
    addSocialWebsiteButton.style.display = "none";

    // Oculta las "X" al salir del modo de edición
    inEditMode = false;
    const removeButtons = document.querySelectorAll(".remove-button");
    removeButtons.forEach(function(button) {
        button.style.display = "none";
    });

    // Resto del código de "Guardar Cambios"
});

function enableEditFields(enabled) {
    const fields = document.querySelectorAll("input, select, textarea");
    fields.forEach(function(field) {
        field.disabled = !enabled;
    });
}

function hideProfilePictureInput() {
    profilePictureInput.style.display = "none";
}

function showProfilePictureInput() {
    profilePictureInput.style.display = "block";
}

enableEditFields(false);
hideProfilePictureInput(); // Ocultamos el campo de selección de la foto al principio

function loadProfilePicture() {
    const input = document.getElementById("profile-picture-input");
    const profilePicture = document.querySelector(".profile-picture img");

    if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = function(e) {
            profilePicture.src = e.target.result;
        };
        reader.readAsDataURL(input.files[0]);
    }
}

// Habilidades
const selectValue = document.querySelector(".select-value");
const skillsDropdown = document.getElementById("skills-dropdown");
const selectedSkillsContainer = document.querySelector(".selected-skills");

// Mostrar el listado de opciones cuando se hace clic en el select
selectValue.addEventListener("click", function() {
    skillsDropdown.style.display = "block";
});

// Manejar la selección de habilidades
skillsDropdown.addEventListener("click", function(e) {
    if (e.target && e.target.nodeName === "LI") {
        const skill = e.target.getAttribute("data-skill");

        // Crear un mini contenedor para la habilidad
        const selectedSkillContainer = document.createElement("span");
        selectedSkillContainer.className = "selected-skill-container";
        selectedSkillContainer.textContent = skill;

        // Agregar un manejador de eventos para eliminar la habilidad del select y del mini contenedor
        selectedSkillContainer.addEventListener("click", function() {
            // Solo eliminar habilidades en modo de edición
            if (inEditMode) {
                selectedSkillContainer.remove(); // Elimina el mini contenedor
                e.target.style.display = "block"; // Devuelve la habilidad al listado
            }
        });

        selectedSkillsContainer.appendChild(selectedSkillContainer); // Agregar el mini contenedor
        e.target.style.display = "none"; // Oculta la habilidad en el listado
        selectValue.textContent = "Selecciona tus habilidades"; // Restaura el valor del select
    }
});

selectValue.style.display = "none";
skillsDropdown.style.display = "none";
skillsDropdown.style.display = "none";

// Ocultar el listado de habilidades cuando se hace clic en cualquier parte fuera de "custom-select"
document.addEventListener("click", function (event) {
    const customSelect = document.getElementById("custom-select");

    if (!customSelect.contains(event.target) && event.target !== selectValue) {
        skillsDropdown.style.display = "none";
    }
});

// Redes socialales
const socialContainer = document.getElementById("social-container");
addSocialWebsiteButton.addEventListener("click", function() {
    const clonedContainer = socialContainer.cloneNode(true);

    // Elimina el texto "Redes sociales" del label en los clones
    const label = clonedContainer.querySelector("label");
    label.textContent = "";

    // Elimina el atributo 'for' del label en los clones
    label.removeAttribute("for");

    // Encuentra el input en los clones y establece su valor en vacío
    const input = clonedContainer.querySelector("input");
    input.value = "";

    const removeButton = document.createElement("button");
    removeButton.textContent = "X";
    removeButton.className = "remove-button";
    clonedContainer.appendChild(removeButton);

    removeButton.addEventListener("click", function() {
        clonedContainer.remove();
    });

    socialContainer.parentNode.insertBefore(clonedContainer, addSocialWebsiteButton);
});
