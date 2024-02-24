import axios from 'axios';



const getMovie = async () => {
   try { 
    const response = await axios.get("http://localhost:8080/api/movies/");
    console.log(response);
      return response.data.data;
   } catch (error) {
      throw error;
   }
};
const deleteMovie = async (id) => {
   try {
     const response = await axios.delete(`http://localhost:8080/api/movies/delete/${id}`);
     return response.data;
   } catch (error) {
     throw error;
   }
 };

 const updatepelicula = async (pelicula) => {
   try {
     const response = await axios.put(`http://localhost:8080/api/movies/update`, pelicula);
     console.log(response.data);
     if (response.data.error) {
       console.error(response.data.message);
     } else {
       console.log(response.data.message);
       this.obtenerPeliculas();
     }
   } catch (error) {
     console.error("Error al actualizar la película:", error);
   }
 };

const getGenres = async () => {
   try { 
    const response = await axios.get("http://localhost:8080/api/genres/");
    console.log(response);
      return response.data;
   } catch (error) {
      throw error;
   }
};





const search = async (param) => {
   try { 
   console.log(param);
   const value = `?term=${param}`
    const response = await axios.get(`http://localhost:8080/api/movies/search${value}`);
    console.log(response);
      return response.data.data;
   } catch (error) {
      throw error;
   }
};
const postMovie = async (pelicula) => {
    try {
       const response = await axios.post("http://localhost:8080/api/movies/", pelicula);
        console.log("hola chinga tu madre",response);
       return response.data;
    } catch (error) {
       throw error;
    }
 };

 const sortedByReleaseDateDesc = async () => {
   try {
      const response = await axios.get("http://localhost:8080/api/movies/sortedByReleaseDateDesc");
      return response.data;
   } catch (error) {
      throw error;
   }
};
   
export default {
   getMovie,
   getGenres,
   postMovie,
   search, 
   sortedByReleaseDateDesc,
   deleteMovie,
   updatepelicula
   
};