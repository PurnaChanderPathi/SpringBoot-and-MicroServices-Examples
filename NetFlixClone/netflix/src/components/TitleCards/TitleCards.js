import React, { useEffect, useRef, useState } from 'react';
import './TitleCards.css';
import { Link } from 'react-router-dom';

const TitleCards = ({ title, category }) => {
  const [apiData, setApiData] = useState([]);
  const cardsRef = useRef();

  const options = {
    method: 'GET',
    headers: {
      accept: 'application/json',
      Authorization: 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2ZmVlNmJlNDlmNWQ0OGRjNjI1ZGNkNzIzMzMwMmE4MSIsInN1YiI6IjY2Njk0YWI0NGUzOTM4NDU2YWVhMmU0ZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.lY2gdarJusF1N6jnnH8hIG1w3pzvt7EwlNDDvtKvGXg'
    }
  };

  const handleWheel = (event) => {
    event.preventDefault();
    cardsRef.current.scrollLeft += event.deltaY;
  };

  useEffect(() => {
    fetch(`https://api.themoviedb.org/3/movie/${category ? category : "now_playing"}?language=en-US&page=1`, options)
      .then(response => response.json())
      .then(response => setApiData(response.results))
      .catch(err => console.error(err));

    const currentCardsRef = cardsRef.current;
    currentCardsRef.addEventListener('wheel', handleWheel);

    // Cleanup function
    return () => {
      currentCardsRef.removeEventListener('wheel', handleWheel);
    };
  }, [category]);

  console.log(apiData);

  return (
    <div className='title-cards'>
      <h2>{title ? title : "Popular on Netflix"}</h2>
      <div className='card-list' ref={cardsRef}>
      console.log(apiData)
        {apiData.length > 0 ? (
          
          apiData.map((card, index) => (
            <Link to={`/player/${card.id}`} className='card' key={index}>
              <img src={`https://image.tmdb.org/t/p/w500${card.backdrop_path}`} alt={card.original_title} />
              <p>{card.original_title}</p>
            </Link>
          ))
        ) : (
          <p>Loading...</p>
        )}
      </div>
    </div>
  );
};

export default TitleCards;
