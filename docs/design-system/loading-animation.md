# Mascot loading animation

Status: visual motion proposal, awaiting review. Open [the live animation](loading-prototype.html). Android implementation remains deferred.

## Visual direction

A **slithering snake in the home mascot's design** chases a rolling red apple marked **0**, travelling left to right. The large eyes, smile, cream muzzle and forked tongue come from the matching [mascot-style head artwork](loading-assets/mascot-head.png). A long, spotted green body and cream belly deform continuously behind it, ending in an integrated tapered tail.

The previous whole-coiled-mascot translation was rejected because it looked like an image sliding. The current scene replaces that layer with a flexible Canvas body. [Artwork provenance](loading-assets/README.md) records the loading-only head source; the approved home and gameplay source art remain unchanged.

The surrounding screen follows the design system: forest background, cream/lime title, Baloo 2 / Nunito, cream LOADING label and three subtle lime dots. Phone is portrait (390 × 844 review frame); tablet is landscape (1024 × 768). No game grid, input buttons, fake percentage or countdown is shown inside the app frame.

## Motion contract

- A 7.2-second cycle moves both actors steadily from left to right.
- The apple stays ahead with a clear gap. Its entire drawing, including the **0**, rotates clockwise in proportion to distance travelled, suggesting a rolling apple rather than a spinning stationary icon.
- The body bends into a travelling S-wave. Over each 7.2-second chase loop, six undulations travel from head toward tail. Phone amplitude is 11px with an 84px wavelength; tablet amplitude is 15px with a 118px wavelength. A smooth neck transition keeps the head aligned. Body points deform independently; the scene does not translate a whole coiled mascot image.
- A faint ground line and soft contact shadows anchor the chase. The mascot stays behind the apple and never bites or catches it.
- Both actors are completely off-screen at the loop boundary, so the reset is invisible. The continuous body is shaded across its local width; spots and belly follow the deformation. The title and loading label stay in place.
- One animation clock drives translation, rolling, mascot motion and dots. Backgrounding does not accumulate catch-up time. Review pause/replay and frame inspection sit outside the app frame.
- With reduced motion requested, show a static mid-chase pose and still loading dots. Explicit Play remains available in the review controls.

The native loading state should end as soon as actual startup work is ready. The animation's loop length must not impose a minimum wait or pretend to measure loading progress. Exit to Home when ready; choose a transition during native integration.

## Assets and native handoff

Reuse the existing forest background. The isolated mascot-style head is scoped to `loading-assets/` for this review; the rest of the snake is continuous geometry. The apple is vector drawing with a live numeral. No new artwork was generated and the approved runtime asset allowlist is unchanged. The review's transform/rotation values are design guidance for later native animation. Native timing, lifecycle behavior and accessibility still require device validation.

[Phone still](previews/loading-phone.png) · [Tablet still](previews/loading-tablet.png). These are browser-rendered inspection frames, not animated runtime assets. To inspect any moment, pause or use the review slider; `?device=phone&phase=0.46` and `?device=tablet&phase=0.46` open the saved pose.

## Review verification

The live browser preview was checked for left-to-right movement, increasing clockwise apple rotation, a constant chase gap, stable pause, correct tablet frame and reduced-motion behavior. Geometry was checked at 1001 animation positions and 21 points along each body for both devices, including off-screen loop boundaries. A regression check confirmed that local body points move by different amounts between frames, ruling out rigid whole-image translation. The source mascot and other approved PNGs retain their prior checksums. Still previews and local documentation links were checked separately.
