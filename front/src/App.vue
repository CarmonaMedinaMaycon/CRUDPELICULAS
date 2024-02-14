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
          <b-button v-b-modal.modal-save-movie class="btnadd">
            <b-icon icon="plus"></b-icon> Registrar pelicula
          </b-button>
        </div>
      </div>
    </template>

    <div class="mb-4">
        <b-col v-for="(pelicula, key) in peliculas" :key="key" lg="3" md="6" sm="12">
          <b-card :title="pelicula.title" style="width: 100%; height: 17rem" class="mb-2">
            <b-card-text class="card-text-scroll">
              <b>Género:</b> {{ pelicula.genres }}<br>
              <b>Descripción:</b> {{ pelicula.description }}<br>
            </b-card-text>
            <template #footer>
              <div class="icono">
               
              </div>
            </template>
          </b-card>
        </b-col>
    </div>
    

  </div>
</template>

<script>
import Movies from './services/Movies';
export default {
  name: "pelis",
  data() {
    return {
      peliculas:{
        id:0,
        title:'',
        description:'',
        genres:[]
      }

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
    // OpenEditModal(movie) {
    //   this.selectedMovie = movie;
    //   this.$bvModal.show('modal-update-movie');
    // },
    // async deleteMovie(id) {
    //   const confirmed = await Swal.fire({
    //     title: "¿Estás seguro de eliminar la película?",
    //     icon: 'warning',
    //     showCancelButton: true,
    //     confirmButtonColor: '#008c6f',
    //     cancelButtonColor: '#e11c24',
    //     confirmButtonText: "Sí, eliminar",
    //     cancelButtonText: 'Cancelar',
    //   });

    //   if (confirmed.isConfirmed) {
    //     try {
    //       const response = await axios.delete(`http://localhost:8080/api-movieBack/peliculas/${id}`);
    //       if (response.data.error) {
    //         console.error(response.data.message);
    //       } else {
    //         Swal.fire({
    //           title: 'Eliminada',
    //           text: 'La película se eliminó correctamente',
    //           icon: 'success',
    //         });
    //         this.fetchData();
    //       }
    //     } catch (error) {
    //       const { data } = error;
    //       this.$swal.fire({
    //         icon: "error",
    //         text: data?.text ? data.text : "Error interno",
    //         timer: 3000,
    //       });
    //     }
    //   }
    // },
  },
  mounted() {
    this.getMovies()
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
  /* añade una barra de desplazamiento vertical si es necesario */
}
</style>
