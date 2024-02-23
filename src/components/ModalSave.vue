<template>
    <div>
        <div>
            <b-modal hide-footer hide-header centered id="modal-save">
                <header class="text-center border-bottom">
                    <p style="font-family: cabin">Registrar pelicula</p>
                </header>
                <main>
                    <form id="registrarPelicula">
                        <b-row>
                            <b-col>
                                <label for="pelicula">Nombre de la pelicula: *</label>

                                <b-form-input v-model="pelicula.name" type="text" class="form-control"
                                    placeholder="Pelicula..." required
                                    :class="{ 'input-border-error': !validateName && pelicula.name.length > 0, 'input-border-success': validateName }"
                                    aria-describedby="input-live-help input-live-feedback" />

                                <div v-if="!validateName && pelicula.name.length > 0" class="invalid-feedback">Formato
                                    inválido</div>


                            </b-col>
                            <b-col>
                                <label for="pelicula">Genero de la pelicula: *</label>
                                <b-form-select v-model="pelicula.genres.id" :state="validateGenres"
                                    :options="options"></b-form-select>

                                <b-form-invalid-feedback :state="validateGenres">
                                    Selecciona un género válido
                                </b-form-invalid-feedback>
                            </b-col>
                        </b-row>

                        <b-row>
                            <b-col>
                                <label for="director">Director: *</label>
                                <b-form-input v-model="pelicula.director" type="text" class="form-control"
                                    placeholder="Director..." required
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
                                <div v-if="!validateReleaseDate && pelicula.releaseDate.length > 0"
                                    class="invalid-feedback">Formato
                                    inválido</div>
                            </b-col>
                        </b-row>

                        <b-row>
                            <b-col>
                                <label for="pelicula">Descripción de la pelicula: *</label>
                                <b-form-textarea id="textarea" v-model="pelicula.description"
                                    placeholder="Describe la pelicula..." rows="3" max-rows="6"
                                    :state="validateDescription"></b-form-textarea>

                                <b-form-invalid-feedback :state="validateDescription">
                                    Formato inválido	
                                </b-form-invalid-feedback>

                            </b-col>
                        </b-row>

                    </form>

                </main>

                <footer class="text-center mt-5">
                    <button class="btn m-1 cancel" @click="onClose">
                        Cancelar
                    </button>
                    <button class="btn m-1 success" @click="save" :disabled="!validateForm" type="submit">
                        Registrar
                    </button>
                </footer>
            </b-modal>
        </div>
    </div>
</template>

<script>
import Swal from 'sweetalert2';
import Movies from '../services/Movies';
export default {
    name: "modal-save",

    data() {
        return {
            pelicula: {
                name: "",
                director: "",
                releaseDate: "",
                description: "",
                genres: {
                    id: null
                },
            },
            selected: null,
            options: [], //opciones de generos

        };
    },
    methods: {
        onClose() {
            this.$bvModal.hide("modal-save");
            this.resetPelicula();

        },
        resetPelicula() {
            this.pelicula = {
                name: "",
                description: "",
                genres: {
                    id: null
                }
            };
        },
        async save() {
            if (this.validateForm) {
                Swal.fire({
                    title: "¿Estás seguro de registrar esta película?",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#008c6f',
                    cancelButtonColor: '#e11c24',
                    confirmButtonText: "Confirmar",
                    cancelButtonText: 'Cancelar',
                }).then(async (result) => {
                    if (result.isConfirmed) {
                        try {
                            if (!this.validateGenres) {

                                Swal.fire({
                                    icon: 'error',
                                    title: 'Error',
                                    text: 'Por favor selecciona un género para la película',
                                });
                                return;
                            }
                            console.log(this.pelicula);
                            await Movies.postMovie(this.pelicula);
                            Swal.fire({
                                title: "¡Guardada!",
                                text: "La película se registró correctamente",
                                icon: "success"
                            });
                            this.onClose();
                            this.$emit('movie-updated');
                        } catch (error) {
                            console.log("Error al guardar la película", error);
                        }
                    }
                });
            } else {
                // Mostrar un mensaje de error indicando que el formulario no es válido
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'Por favor completa todos los campos correctamente',
                });
            }
        },


        async fetchGenres() {
            try {
                const response = await Movies.getGenres();
                this.options = response.data.map(genre => ({ value: genre.id, text: genre.name }));
            } catch (error) {
                console.error("Error al obtener los géneros:", error);
            }
        }



    },
    mounted() {
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


    }

}
</script>

<style scoped>
.success {
    font-family: Cabin;
    background-color: #009475;
    color: white;
}

.cancel {
    font-family: Cabin;
    background-color: brown;
    color: black;
}

.input-border-error {
    border: 1px solid red !important;
}

.input-border-success {
    border: 1px solid green !important;
}
</style>