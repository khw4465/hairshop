let btn = document.querySelector("#bttn");
let nav = document.querySelector("#nav");

btn.addEventListener("click",()=>{
    btn.classList.toggle("active");
    nav.classList.toggle("active");
});
nav.addEventListener("mouseleave",()=>{
    btn.classList.remove("active");
    nav.classList.remove("active");
});