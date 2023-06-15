function filterSearchMovies() {
    const input = document.getElementById("movieSearchInput");
    const searchText = input.value.toUpperCase();
    const movies = document.getElementById("listMovies").children;
    for (let i = 0; i < movies.length; i++) {
        if(movies[i].id === "listHeader") continue; //skip all elements with class "ignoreInJs"

        const title = movies[i].getAttribute("data-title");
        if (title.toUpperCase().includes(searchText)) {
            if(movies[i].className.includes("d-none"))
                movies[i].className = "bg-dark list-group-item d-flex justify-content-between align-items-start"
        } else {
            movies[i].className += " d-none";
        }
    }
}