<template>
  <div class="body">
    <template>
      <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
          <b>
            <h3>Peliculas <b-icon icon="camera-reels"></b-icon></h3>
          </b>
        </div>
        <div class="bodybutton">
          <b-button v-b-modal.modal-save class="btnadd mr-2">
            <b-icon icon="plus"></b-icon> Registrar película
          </b-button>
          <b-button squared variant="info" @click="sortMoviesByReleaseDateDesc()"> <b-icon icon="calendar"></b-icon>
            Ordenar películas</b-button>
        </div>

      </div>
    </template>
  
    <div class="input-group">
      <input type="text" class="form-control" placeholder="Buscar pelicula" aria-label="Buscar pelicula"
        v-model="search" />
      <b-button @click="getSearch(search)" class="input-group-text bg-primary text-secondary" readonly>buscar</b-button>
    </div>
    <br>
    <b-row class="mb-4">
      <b-col v-for="(pelicula, key) in peliculas" :key="key" lg="3" md="6" sm="12">
        <b-card :title="pelicula.name"
          img-src="https://imgs.search.brave.com/5yLy2Vd-AcHQFOAMoQtlMkUY5VNtYEPsmMJ2pLqI1HA/rs:fit:860:0:0/g:ce/aHR0cHM6Ly93YWxs/cGFwZXJzZXQuY29t/L3cvZnVsbC9iLzcv/OC80MDQzNy5qcGc"
          class="mb-2">
          <b-card-text class="card-text-scroll">
            <b>Género:</b> {{ pelicula.genres ? pelicula.genres.name : 'Sin género' }}<br>
            <b>Director:</b> {{ pelicula.director }}<br>
            <b>Descripción:</b> {{ pelicula.description }}<br>
            <b>Fecha de estreno:</b> {{ pelicula.releaseDate }}<br>
          </b-card-text>
          <b-button v-b-modal.modal-update variant="primary" class="mr-2">Editar</b-button>
          <b-button @click="deleteMovie(pelicula.id)" variant="danger">Eliminar</b-button>
        </b-card>
      </b-col>
    </b-row>
    <ModalUpdate />
    <ModalSave />


  </div>
</template>

<script>
import Movies from './services/Movies';
import ModalSave from './components/ModalSave.vue';
import ModalUpdate from './components/ModalUpdate.vue';
import Swal from 'sweetalert2';
export default {
  components: { ModalSave, ModalUpdate },
  name: "pelis",
  data() {
    return {
      peliculas: {
        id: 0,
        name: '',
        director: '',
        description: '',
        releaseDate: '',
        genres: {
          id: 0,
          description: '',
          name: ''
        }
      },
      genres: {
        id: 0,
        description: '',
        name: ''
      },
      search: '',

    }
  },

  methods: {


    async getMovies() {
      try {
        const response = await Movies.getMovie();
        console.log("soy la data", response);
        this.peliculas = response;
      } catch (error) {
        console.log("trono papito", error);
      }
    },

    async getGenres() {
      try {
        const response = await Movies.getGenres();
        console.log("soy la data generoos", response);
        this.genres = response;
      } catch (error) {
        console.log("trono papito", error);
      }
    },



    async getSearch(search) {
      try {
        console.log();
        const response = await Movies.search(search);
        console.log("soy la data", response);
        this.peliculas = response;
      } catch (error) {
        console.log("trono papito", error);
      }
    },

    async sortMoviesByReleaseDateDesc() {
      try {
        const response = await Movies.sortedByReleaseDateDesc();
        this.peliculas = response.data;
        console.log(response);
      } catch (error) {
        console.error("Error al ordenar las películas:", error);
      }
    },
    async deleteMovie(id) {
      Swal.fire({
        title: "¿Estás seguro de eliminar esta película?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#008c6f',
        cancelButtonColor: '#e11c24',
        confirmButtonText: "Confirmar",
        cancelButtonText: 'Cancelar',
      }).then(async (result) => {
        if (result.isConfirmed) {
          try {
            const response = await Movies.deleteMovie(id);
            console.log("Película eliminada:", response);
            Swal.fire({
              title: "Eliminada!",
              text: "La película se eliminó correctamente",
              icon: "success"
            });
            this.getMovies();
          } catch (error) {
            console.error("Error al eliminar la película:", error);
          }
        }
      });
    }

  },
  mounted() {
    this.getMovies(),
      this.getGenres()
  },
};
</script>

<style>
.body {
  padding-left: 20px;
  padding-right: 20px;
  margin-top: 10px;
}

@media screen and (max-width: 768px) {
  .body {
    padding-left: 10px;
    padding-right: 10px;
  }
}


.btnadd {
  background-color: #089779;
  padding-right: px;
}

.icono {
  text-align: end;
}

.card-text-scroll {
  max-height: 150px;
  overflow-y: auto;
}
</style>
