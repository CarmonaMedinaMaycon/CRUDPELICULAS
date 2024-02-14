import axios from 'axios';



const getMovie = async () => {
   try {
    const response = await axios.get("https://pokeapi.co/api/v2/pokemon/ditto");
    console.log(response);
      return response.data;
   } catch (error) {
      throw error;
   }
};
// const postCar = async (year, brand, model, serie) => {
//     const params = {
//         year, brand, model, serie
//     };
//     try {
//        const response = await axios.post("http://localhost:8080/api/vehiculos/", null, { params });
 
//        return response.data;
//     } catch (error) {
//        throw error;
//     }
//  };

export default {
   getMovie,
   
};