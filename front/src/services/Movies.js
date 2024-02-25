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
const postMovie = async (pelicula) => {
   
    try {
       const response = await axios.post("http://localhost:8080/api/movies/", pelicula);
        console.log("hola chinga tu madre",response);
       return response.data;
    } catch (error) {
       throw error;
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

export default {
   getMovie,
   postMovie,
   getGenres
   
};