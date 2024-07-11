3x3 piškvorky pomocí JavaFX

Při dosažení 6 objektů (možné nastavit jiný počet atributem "maxPocet") se první zadaný objekt zbarví do červena a při dalším tahu se smaže a další se nastaví na červeno.

Po spojení 3 stejných znaků se objeví popup s možností hrát znovu. Při kliknutí na cancel se program zeptá, zda chce uživatel zavřít program.

Pro používání je potřeba mít nainstalovanou JavaFX17.0.10, připojit JavuFX do library, jako JavaFX17 a v VM Options změnit "--module-path "C:\Program Files\javafx-sdk-17.0.10\lib" --add-modules javafx.controls,javafx.fxml" na cestu ke své JavaFX knihovně. Tedy pozměnit jen tuto část textu: "C:\Program Files\javafx-sdk-17.0.10\lib".
