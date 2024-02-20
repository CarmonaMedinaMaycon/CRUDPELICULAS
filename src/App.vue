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
          <b-button v-b-modal.modal-save class="btnadd">
            <b-icon icon="plus"></b-icon> Registrar pelicula
          </b-button>
        </div>
      </div>
    </template>
    <div class="input-group">
              <input type="text" class="form-control" placeholder="Buscar pelicula" aria-label="Buscar pelicula"
                     v-model="search"/>
                     <b-button @click="getSearch(search)" class="input-group-text bg-primary text-secondary" readonly>buscar</b-button>
            </div>
    <b-row class="mb-4">
        <b-col v-for="(pelicula, key) in peliculas" :key="key" lg="3" md="6" sm="12">
          <b-card :title="pelicula.name"  img-src="https://imgs.search.brave.com/5yLy2Vd-AcHQFOAMoQtlMkUY5VNtYEPsmMJ2pLqI1HA/rs:fit:860:0:0/g:ce/aHR0cHM6Ly93YWxs/cGFwZXJzZXQuY29t/L3cvZnVsbC9iLzcv/OC80MDQzNy5qcGc"  class="mb-2">
            <b-card-text class="card-text-scroll">
              <b>Género:</b> {{ pelicula.genres.name }}<br>
              <b>Director:</b> {{ pelicula.director }}<br>
              <b>Descripción:</b> {{ pelicula.description }}<br>
            </b-card-text>
         
          </b-card>
        </b-col>
      </b-row>
      <ModalSave/>


  </div>
</template>

<script>
import Movies from './services/Movies';
import ModalSave from './components/ModalSave.vue';
export default {
  components: { ModalSave },
  name: "pelis",
  data() {
    return {
      peliculas:{
        id:0,
        name:'',
        director:'',
        description:'',
        genres:{
          id:0,
          description:'',
          name:''
        }
      },
      search:'',

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
