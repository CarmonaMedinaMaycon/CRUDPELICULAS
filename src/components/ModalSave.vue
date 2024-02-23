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
                                <label for="pelicula">Descripción de la pelicula: *</label>
                                <b-form-textarea id="textarea" v-model="pelicula.description"
                                    placeholder="Describe la pelicula..." rows="3" max-rows="6"
                                    :state="validateDescription"></b-form-textarea>

                                <b-form-invalid-feedback :state="validateDescription">
                                    No se aceptan caracteres especiales ni espacios en blanco
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
                        this.$emit('movie-updated');
                    } catch (error) {
                        console.log("Error al guardar la pelicula", error);
                    }

                }
            });
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
            const regex = /^[a-zA-Z0-9]+$/;
            return this.pelicula.name.length > 0 && this.pelicula.name.length < 100 && regex.test(this.pelicula.name);
        },
        validateDescription() {
            const regex = /^[a-zA-Z0-9]+$/;
            return this.pelicula.description.length > 0 && this.pelicula.description.length < 100 && regex.test(this.pelicula.description);
        },
        validateGenres() {
            return this.pelicula.genres.id !== null;
        },
        validateForm() {
            return this.validateName && this.validateDescription && this.validateGenres;
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