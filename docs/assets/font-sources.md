# Bundled Home fonts

T-004, retrieved 2026-11-22 from the official Google Fonts repository.
Both fonts use the SIL Open Font License; bundled copies are in
`app/src/main/res/raw/baloo2_license.txt` and `nunito_license.txt`.

| Font | Source | Source SHA-256 | Bundled static SHA-256 |
|---|---|---|---|
| Baloo 2 ExtraBold 800 | [variable TTF](https://raw.githubusercontent.com/google/fonts/main/ofl/baloo2/Baloo2%5Bwght%5D.ttf) | d47a6852548059b1db49a1319d06d499d546c3fa2237cf9eee9c43c8abb025c2 | 61bdf7f21385955c7be41c53c5e53924ce1febe767b5dcb8a637e7667ba7b33f |
| Nunito Bold 700 | [variable TTF](https://raw.githubusercontent.com/google/fonts/main/ofl/nunito/Nunito%5Bwght%5D.ttf) | bb55a5ca5c2042335b3991af27c4d0705d0ef41cac6164ac737fd8f2a1e85207 | da730c7d1b296e594385837c9b092695cd57d246684474392f6ce42a8f912154 |

Generated static instances with Python 3.12.7 and FontTools 4.68.0:

```python
from fontTools.ttLib import TTFont
from fontTools.varLib.instancer import instantiateVariableFont
instantiateVariableFont(TTFont(source), {"wght": weight}, inplace=True).save(destination)
```

All variable axes are instantiated; output resources are
`res/font/baloo2_extra_bold.ttf` and `res/font/nunito_bold.ttf`.
This avoids depending on variable-font support on API 23. Device rendering
coverage is tracked separately in [verification](../verification/T-004/README.md).

The mascot and forest resource copies retain the hashes in
[asset-manifest.json](asset-manifest.json); neither image was transformed.
