import defaultTheme from 'tailwindcss/defaultTheme';
import forms from '@tailwindcss/forms';

/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/main/resources/templates/**/*.html'],
  theme: {
    extend: {
          fontFamily: {
                        sans: ['Figtree', ...defaultTheme.fontFamily.sans],
                    },
    },
  },
  plugins: [forms],
}

