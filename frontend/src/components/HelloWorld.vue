<!-- 
  https://github.com/pmbanugo/realtime-datatable-vue
  https://medium.freecodecamp.org/how-to-build-a-real-time-editable-data-table-in-vue-js-46b7f0b11684
 -->
<template>
  <div>
    <v-navigation-drawer fixed :mini-variant="miniVariant" :clipped="clipped" v-model="drawer" app>
      </v-navigation-drawer>
      <v-toolbar fixed app :clipped-left="clipped">
        <v-toolbar-title v-text="title"></v-toolbar-title>
        <v-spacer></v-spacer>
      </v-toolbar>
      <main>
        <v-content>
          <v-container fluid>
            <div>
              <v-dialog v-model="dialog" max-width="600px">
                <v-btn slot="activator" @click="editBoxTitle='Create new movie'" color="primary" dark class="mb-2">New Movie</v-btn>
                <v-card>
                  <v-card-title>
                    <span class="headline">{{editBoxTitle}}</span>
                  </v-card-title>
                  <v-card-text>
                    <v-container grid-list-md text-xs-left>
                      <v-layout row wrap>
                        <v-flex xs12>
                          <v-text-field :disabled="editedIndex > -1" v-model="editedItem.title" label="Title"></v-text-field>
                        </v-flex>
                      </v-layout>
                      <v-layout row wrap>
                        <v-flex xs6>
                          <v-combobox v-model="editedItem.genre" :items="genres" item-text="title" label="Pick a Genre"></v-combobox>
                        </v-flex>
                      </v-layout>
                      <v-layout row wrap>
                        <v-flex xs6>
                          <v-card-text class="px-0">Rating</v-card-text>
                          <v-rating v-model="editedItem.rating"></v-rating>
                        </v-flex>
                      </v-layout>
                    </v-container>
                  </v-card-text>
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" flat @click.native="closeDialog">Cancel</v-btn>
                    <v-btn color="blue darken-1" flat @click.native="addMovieToList">Save</v-btn>
                  </v-card-actions>
                </v-card>
              </v-dialog>
              <v-data-table :headers="headers" :items="movies" hide-actions class="elevation-1">
                <template slot="items" slot-scope="props">
                  <td>{{ props.item.movieId }}</td>
                  <td class="text-xs-left">{{ props.item.title }}</td>
                  <td class="text-xs-left">
                    <v-combobox v-model="props.item.genre.title" chips readonly></v-combobox>
                  </td>
                  <td class="text-xs-left">
                    <v-rating readonly v-model="props.item.rating"></v-rating>
                  </td>
                  <td class="justify-center layout px-0">
                    <v-btn icon class="mx-0" @click="editCurrentMovie(props.item)">
                      <v-icon color="teal">edit</v-icon>
                    </v-btn>
                    <v-btn icon class="mx-0" @click="deleteMovieFromList(props.item)">
                      <v-icon color="pink">delete</v-icon>
                    </v-btn>
                  </td>
                </template>
              </v-data-table>
            </div>
          </v-container>
        </v-content>
      </main>
      <v-footer :fixed="fixed" app>
        <span>&copy; 2017</span>
      </v-footer>
      <v-card>
        <v-snackbar v-model="errorToast.display" :color="errorToast.color" :multi-line="errorToast.mode === 'multi-line'" :timeout="errorToast.timeout">
          {{ errorToast.text }}
          <v-btn dark flat @click="errorToast.display = false">Close</v-btn>
        </v-snackbar>
      </v-card>
  </div>
</template>

<script>
import axios from "axios";
axios.defaults.baseURL = "http://localhost:8088/api";

export default {
  data: () => ({
    clipped: false,
    drawer: false,
    fixed: false,
    items: [],
    miniVariant: false,
    right: true,
    rightDrawer: false,
    title: "Movie rating app",
    editBoxTitle: "Create new movie",
    dialog: false,
    headers: [
      { text: "Movie#", align: "left", value: "movieId" },
      { text: "Title", value: "title" },
      { text: "Genre", value: "genre" },
      { text: "Rating", value: "rating" },
      { text: "Actions", value: "name", sortable: false }
    ],
    movies: [],
    genres: [],
    editedIndex: -1,
    editedItem: {
      movieId: 0,
      title: "",
      rating: 1,
      genre: {
        genreId: 0,
        title: "Comedy"
      }
    },
    defaultItem: {
      movieId: 0,
      title: "",
      rating: 1,
      genre: {
        genreId: 0,
        title: "Comedy"
      }
    },
    errorToast: {
        display: false,
        color: 'error',
        mode: 'multi-line',
        timeout: 9000,
        text: ''
    }
  }),
  created: function(){
    return this.fetchGenres().then(() => {
      return this.fetchMovies()
    });
  },
  methods: {
    closeDialog() {
      this.dialog = false
      setTimeout(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      }, 300)
    },
    addMovieToList() {
      if (this.editedIndex > -1) {
        // update
        return this
          .updateMovie(this.editedItem)
          .then(() => {
            this.closeDialog()
          });
      } else {
        // create
        return this
          .createMovie(this.editedItem)
          .then(() => {
            this.closeDialog()
          });
      }
    },
    editCurrentMovie(movie) {
      this.editBoxTitle = "Edit movie";
      this.editedIndex = this.movies.indexOf(movie);
      this.editedItem = Object.assign({}, movie);
      this.dialog = true;
    },
    deleteMovieFromList(movie) {
      var index = this.movies.indexOf(movie)
      if(index > -1 && confirm('Are you sure you want to delete this movie?')){
        // delete
        return this.deleteMovie(movie);
      }

      return Promise.resolve();
    },
    fetchGenres() {
      return axios
        .get("/genres")
        .then(response => {
          this.genres = response.data;
        })
        .catch(e => {
          this.errorToast.text = e;
          this.errorToast.display = true;
        });
    },
    fetchMovies() {
      return axios
        .get("/movies")
        .then(response => {
          this.movies = response.data;
        })
        .catch(e => {
          this.errorToast.text = e;
          this.errorToast.display = true;
        });
    },
    createMovie(movie) {
      if (!this.movies.find(m => m.title === movie.title)) {
        return axios
          .post("/movies", movie)
          .then(response => {
            if (response.status === 201) {
              return this.fetchMovies();
            } else if(response.status === 409){
              this.errorToast.text = `A movie with a title of '${movie.title}' already exists.`;
              this.errorToast.display = true;

              return this.fetchMovies();
            } else {
              this.errorToast.text = `Unable to create movie with title '${movie.title}'. Server responded: ${response.statusText}(${response.status})`;
              this.errorToast.display = true;
            }
          })
          .catch(e => {
            this.errorToast.text = `Unable to create movie with title '${movie.title}'. Server responded ${e.response.statusText}(${e.response.status})`;
            this.errorToast.display = true;
          });
      }

      return Promise.resolve();
    },
    deleteMovie(movie) {
      if (this.movies.find(m => m.title === movie.title)) {
        return axios
          .delete(`/movies/${movie.movieId}`)
          .then(response => {
            if (response.status === 200) {
              return this.fetchMovies();
            } else {
              this.errorToast.text = `Unable to delete movie with title '${movie.title}(Id=${movie.movieId})'. Server responded: ${response.statusText}(${response.status})`;
              this.errorToast.display = true;
            }
          })
          .catch(e => {
            this.errorToast.text = `Unable to delete movie with title '${movie.title}(Id=${movie.movieId})'. Server responded ${e.response.statusText}(${e.response.status})`;
            this.errorToast.display = true;
          });
      }

      return Promise.resolve();
    },
    updateMovie(movie) {
      return axios
        .patch(`/movies/${movie.movieId}`, movie)
        .then(response => {
          if (response.status === 200) {
            return this.fetchMovies();
          } else {
            this.errorToast.text = `Unable to modify movie with title '${movie.title}(Id=${movie.movieId})'. Server responded: ${response.statusText}(${response.status})`;
            this.errorToast.display = true;
          }
        })
        .catch(e => {
            this.errorToast.text = `Unable to modify movie with title '${movie.title}(Id=${movie.movieId})'. Server responded ${e.response.statusText}(${e.response.status})`;
            this.errorToast.display = true;
        });
    }
  }
};
</script>