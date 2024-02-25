<template>
  <div style="height: 2000px" class="content">
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

    <b-row>
      <b-col>
        <form id="registrarPelicula" draggable="true" @dragstart="dragStart">
          <b-row>
            <b-col>
              <label for="pelicula">Nombre de la pelicula: *</label>

              <b-form-input v-model="pelicula.name" type="text" class="form-control" placeholder="Pelicula..." required
                :class="{ 'input-border-error': !validateName && pelicula.name.length > 0, 'input-border-success': validateName }"
                aria-describedby="input-live-help input-live-feedback" />

              <div v-if="!validateName && pelicula.name.length > 0" class="invalid-feedback">Formato
                inválido</div>


            </b-col>
            <b-col>
              <label for="pelicula">Genero de la pelicula: *</label>
              <b-form-select v-model="pelicula.genres.id" :state="validateGenres" :options="options"></b-form-select>

              <b-form-invalid-feedback :state="validateGenres">
                Selecciona un género válido
              </b-form-invalid-feedback>
            </b-col>
          </b-row>

          <b-row>
            <b-col>
              <label for="director">Director: *</label>
              <b-form-input v-model="pelicula.director" type="text" class="form-control" placeholder="Director..."
                required
                :class="{ 'input-border-error': !validateDirector && pelicula.director.length > 0, 'input-border-success': validateDirector }"
                aria-describedby="input-live-help input-live-feedback" />
              <div v-if="!validateDirector && pelicula.director.length > 0" class="invalid-feedback">
                Formato
                inválido</div>
            </b-col>
            <b-col>
              <label for="releaseDate">Fecha de lanzamiento: *</label>
              <b-form-input v-model="pelicula.releaseDate" type="date" class="form-control"
                placeholder="Fecha de lanzamiento..." required
                :class="{ 'input-border-error': !validateReleaseDate && pelicula.releaseDate.length > 0, 'input-border-success': validateReleaseDate }"
                aria-describedby="input-live-help input-live-feedback" />
              <div v-if="!validateReleaseDate && pelicula.releaseDate.length > 0" class="invalid-feedback">Formato
                inválido</div>
            </b-col>
          </b-row>

          <b-row>
            <b-col>
              <label for="pelicula">Descripción de la pelicula: *</label>
              <b-form-textarea id="textarea" v-model="pelicula.description" placeholder="Describe la pelicula..." rows="3"
                max-rows="6" :state="validateDescription"></b-form-textarea>

              <b-form-invalid-feedback :state="validateDescription">
                Formato inválido
              </b-form-invalid-feedback>

            </b-col>
          </b-row>

        </form>
      </b-col>
    </b-row>

    <!-- Área de películas donde se pueden soltar las películas registradas -->
    <div id="areaPeliculas" @drop.prevent="handleDrop" @dragover.prevent>
      <b-card></b-card>
    </div>
    <!-- Área de películas donde se pueden soltar las películas registradas -->
    <div id="areaPeliculas" @drop.prevent="handleDrop" @dragover.prevent>
      <b-row class="mb-4" v-show="showElement">
        <TransitionGroup name="slideDown" tag="div" class="d-flex flex-wrap">
          <b-col v-for="(pelicula, key) in peliculas" :key="key" lg="3" md="6" sm="12" class="mb-4">
            <b-card :title="pelicula.name"
              img-src="https://imgs.search.brave.com/5yLy2Vd-AcHQFOAMoQtlMkUY5VNtYEPsmMJ2pLqI1HA/rs:fit:860:0:0/g:ce/aHR0cHM6Ly93YWxs/cGFwZXJzZXQuY29t/L3cvZnVsbC9iLzcv/OC80MDQzNy5qcGc"
              class="mb-2">
              <b-card-text class="card-text-scroll">
                <b>Género:</b> {{ pelicula.genres.name }}<br />
                <b>Director:</b> {{ pelicula.director }}<br>
                <b>Descripción:</b> {{ pelicula.description }}<br>
                <b>Fecha de estreno:</b> {{ pelicula.releaseDate }}<br>
              </b-card-text>
            </b-card>
          </b-col>
        </TransitionGroup>
      </b-row>
    </div>
    <ModalSave @reloadMovies="getMovies" />
  </div>
</template>

<script>
import Movies from "./services/Movies";
import ModalSave from "./components/ModalSave.vue";
export default {
  components: { ModalSave },
  name: "pelis",
  data() {
    return {
      selected: null,
      options: [],
      showElement: true,
      lastScrollPosition: 0,
      pelicula: {
        id: 0,
        name: "",
        description: "",
        director: "",
        releaseDate: "",
        genres: {
          id: 0,
          description: "",
          name: "",
        },
      },
      peliculas: {
        id: 0,
        name: "",
        description: "",
        genres: {
          id: 0,
          description: "",
          name: "",
        },
      },
    };
  },

  methods: {

    dragStart(event) {
      // Inicia el arrastre del formulario de registro de películas
      event.dataTransfer.setData("pelicula", JSON.stringify(this.pelicula));
    },
    handleDrop(event) {
      // Manipula el evento de soltar películas en el área de películas
      const peliculaData = JSON.parse(event.dataTransfer.getData("pelicula"));
      this.registrarPelicula(peliculaData);
    },
    async registrarPelicula(peliculaData) {
      try {
        await Movies.postMovie(peliculaData);
        this.getMovies(); // Actualiza la lista de películas después de registrar una nueva
      } catch (error) {
        console.error("Error al registrar la película:", error);
      }
    },

    async save() {
      Swal.fire({
        title: "¿Estás seguro de registrar etsa pelicula?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#008c6f',
        cancelButtonColor: '#e11c24',
        confirmButtonText: "Confirmar",
        cancelButtonText: 'Cancelar',
      }).then(async (result) => {
        if (result.isConfirmed) {
          try {
            console.log(this.pelicula);
            await Movies.postMovie(this.pelicula);

            Swal.fire({
              title: "¡Guardada!",
              text: "La pelicula se registró correctamente",
              icon: "success"
            });
            this.onClose();

            this.$emit('reloadMovies')
          } catch (error) {
            console.log("Error al guardar la pelicula", error);
          }

        }
      });
    },
    onScroll() {
      // Obtiene la posición actual del scroll
      const currentScrollPosition =
        window.pageYOffset || document.documentElement.scrollTop;
      console.log(currentScrollPosition);

      // La función abs para tener valores absolutos y se delimita con un offset o bien llamado
      // margen para que el valor de la posición sea después de desplazarce y no desde que uno se desplaza
      if (Math.abs(currentScrollPosition - this.lastScrollPosition) < 60) {
        return;
      }
      // aqui determinamos si la posición es mayor a la posición anterior. Entonces, si lo es, mostramos el elemento.
      this.showElement = currentScrollPosition < this.lastScrollPosition;
      //
      this.lastScrollPosition = currentScrollPosition;
    },
    async getMovies() {
      try {
        const response = await Movies.getMovie();
        console.log("soy la data", response);
        this.peliculas = response;
      } catch (error) {
        console.log("trono papito", error);
      }
    },
    async fetchGenres() {
      try {
        const response = await Movies.getGenres();
        this.options = response.data.map(genre => ({ value: genre.id, text: genre.name }));
      } catch (error) {
        console.error("Error al obtener los géneros:", error);
      }
    },
  },

  mounted() {
    window.addEventListener("scroll", this.onScroll);
    this.getMovies();
    this.fetchGenres();
  },
  computed: {
    validateName() {
      //const regex = /^[a-zA-Z0-9\s!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~ñ]+$/; sin acentos
      const regex = /^[a-zA-Z0-9\s!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~áéíóúÁÉÍÓÚñÑ]+$/;
      return this.pelicula.name.length > 0 && this.pelicula.name.length < 50 && regex.test(this.pelicula.name);
    },
    validateDirector() {
      const regex = /^[a-zA-Z0-9\s!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~áéíóúÁÉÍÓÚñÑ]+$/;
      return this.pelicula.director.length > 0 && this.pelicula.director.length < 30 && regex.test(this.pelicula.director);
    },
    validateDescription() {
      const regex = /^[a-zA-Z0-9\s!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~áéíóúÁÉÍÓÚñÑ]+$/;
      return this.pelicula.description.length > 0 && this.pelicula.description.length < 100 && regex.test(this.pelicula.description);
    },
    validateGenres() {
      return this.pelicula.genres.id !== null;
    },
    validateForm() {
      return this.validateName && this.validateDescription && this.validateGenres;
    },
    validateReleaseDate() {
      // Expresión regular para el formato YYYY-MM-DD
      const regex = /^\d{4}-\d{2}-\d{2}$/;
      // Verificar que la fecha cumpla con el formato YYYY-MM-DD
      return regex.test(this.pelicula.releaseDate);
    },


  },
  beforeDestroy() {
    window.removeEventListener("scroll", this.onScroll);
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

.hidden {
  display: none;
}

.card-text-scroll {
  max-height: 150px;
  overflow-y: auto;
  /* añade una barra de desplazamiento vertical si es necesario */
}
</style>
