import React, { useState } from 'react';
import { Stack, Rating } from '@mui/material';
import FavoriteIcon from '@mui/icons-material/Favorite';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';

export const MuiRating = () => {
  const [value, setValue] = useState(null); // Change to number for readOnly and (highlightSelectedOnly)
  console.log({ value });

  const handleOnChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <Stack spacing={2}>
      <Rating
        value={value}
        size="large"
        icon={<FavoriteIcon fontSize="inherit" color="error" />}
        emptyIcon={<FavoriteBorderIcon fontSize="inherit" />}
        // readOnly
        // highlightSelectedOnly
        precision={0.5}
        onChange={handleOnChange}
      />
    </Stack>
  );
};
