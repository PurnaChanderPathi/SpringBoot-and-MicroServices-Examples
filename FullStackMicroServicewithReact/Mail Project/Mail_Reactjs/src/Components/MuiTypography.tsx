import { Typography } from "@mui/material"

export const MuiTypography = () => {
  return (
    <div>
        <Typography variant="h1">h1 heading</Typography>
        <Typography variant="h2">h2 heading</Typography>
        <Typography variant="h3">h3 heading</Typography>
        <Typography variant="h4" component='h1' gutterBottom>h4 heading</Typography>
        <Typography variant="h5">h5 heading</Typography>
        <Typography variant="h6">h6 heading</Typography>

        <Typography variant="subtitle1">Subtitle 1</Typography>
        <Typography variant="subtitle2">Subtitle 2</Typography>

        <Typography variant="body1">JSX and TSX are syntax extensions that allow developers to write HTML-like syntax in their JavaScript or TypeScript code. JSX is the original syntax extension used in React, while TSX combines JSX syntax with TypeScript's type system.</Typography>
        <Typography variant="body2">JSX and TSX are syntax extensions that allow developers to write HTML-like syntax in their JavaScript or TypeScript code. JSX is the original syntax extension used in React, while TSX combines JSX syntax with TypeScript's type system.</Typography>
    </div>
  )
}
