document.addEventListener("DOMContentLoaded", function () {
  const input = document.querySelector(".principal__input");

  input.addEventListener("input", function () {
    if (this.value !== "") {
      this.classList.add("expanded");
    } else {
      this.classList.remove("expanded");
    }
  });
});
