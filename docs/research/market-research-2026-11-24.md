# Snake & Math: market research

**Date:** 2026-11-24
**Scope:** Should this app be built as specified in the [PRD](../snake-math-prd.md) and [screen prototype](../design-system/screens-prototype.html), and for what goal?
**Status:** Research only. Recommendations are not approved requirements, and nothing here changes the PRD.

**Evidence tags**
- **[V]** read directly from the cited page.
- **[S]** taken from a web-search summary; the source page was not read. Check before quoting.
- **[I]** inference or judgement.

## 1. Executive summary

| Goal | Verdict | Main reason |
|---|---|---|
| Portfolio and personal project | **Go** | The market does not need to be large. Engine, renderer, tests and architecture are the value. |
| Useful learning app for children | **Conditional** | Need is real and the research is encouraging but mixed. The current MDAS loop needs a test with children and wrong-answer feedback. |
| Commercial product, as specified | **No-go** | The niche is crowded with near-identical apps that almost nobody installs, and V1 has no monetization or distribution. |

Thirteen snake-plus-arithmetic apps visible on Google Play have roughly **5,900 to 12,600 lifetime installs combined**. One app holds most of them. Four of the thirteen carry the Teacher Approved badge, and none of those four has passed the 100+ bucket. Building a better snake will not, by itself, fix that.

The working title also collides with existing apps. The top US result for "snake & math" is **Math and Snake**.

## 2. Key findings

### 2.1 The exact concept already exists, many times

Google Play listings read 2026-11-24, US storefront **[V]**. Play shows bucket floors, so "100+" means 100–499.

| App | Installs | Teacher Approved | Ads / IAP | Updated | Overlap |
|---|---:|:---:|---|---|---|
| [Math snake](https://play.google.com/store/apps/details?id=com.strict.mkenin.mathsnake) (Strict games) | 5K+ | – | Ads | 2025-10-22 | Direct |
| [Sum Snake](https://play.google.com/store/apps/details?id=com.maumyrtille.sumsnake) | 500+ | – | None shown | 2025-05-02 | Adjacent: reach a target sum |
| [Math and Snake](https://play.google.com/store/apps/details?id=fun.flappydevs.snake.math.learning) (flappydevs) | 100+ | Yes | IAP | 2026-10-05 | **Closest** |
| [Math Snake](https://play.google.com/store/apps/details?id=math.snake) (Verneri Hartus) | 100+ | – | Ads + IAP | 2025-10-11 | Direct; lists order of operations |
| [Snake Vs Math Blocks](https://play.google.com/store/apps/details?id=com.snake.block.math.number.arcade.games.challenges) | 50+ | – | Ads + IAP | 2025-11-21 | Adjacent |
| [Snake Math](https://play.google.com/store/apps/details?id=com.Ninorock.snakemath) (Ninorock) | 50+ | Yes | Ads | 2026-02-26 | Direct |
| [Snake Math](https://play.google.com/store/apps/details?id=com.johnguaz.SnakeMath) (johnguaz) | 10+ | – | None shown | 2026-11-04 | Direct |
| [Math Snake](https://play.google.com/store/apps/details?id=com.mathsnake.app) (Tararam) | 10+ | Yes | None shown | 2026-03-28 | Direct |
| [Math Brain & Snake](https://play.google.com/store/apps/details?id=com.maths.kulthemadam) | 10+ | – | Ads | 2026-07-22 | Direct |
| [Multiplication Snake](https://play.google.com/store/apps/details?id=com.manycalculators.multiplicationsnake) | 10+ | Yes | Ads | 2026-10-03 | Direct |
| [Snake Sum](https://play.google.com/store/apps/details?id=games.joco.snake.sum) | 10+ | – | None shown | 2026-09-03 | Adjacent |
| [Math Snake \| Brain Training](https://play.google.com/store/apps/details?id=com.snakegame.mathgame.learningame) | 10+ | – | Ads | 2025-11-26 | Direct |
| [Snake Game: Challenge Math](https://play.google.com/store/apps/details?id=com.snakemath.endplif) | 10+ | – | Ads | 2023-09-18 | Direct; two answer apples |

- Totals: floors sum to 5,870; ceilings of the next buckets sum to about 12,550. This is a search-derived list, not a full census.
- **Math and Snake** targets ages 5–12 with five worlds, number ranges, no ads, a Teacher Approved badge, a shop and recent updates **[V]**. AppGoblin estimates 350 installs and 22 monthly actives since its June 2025 release, with no ratings **[V, third-party estimate]** ([source](https://appgoblin.info/apps/fun.flappydevs.snake.math.learning)).
- The mechanic is also free on the web: [mathsnake.cloud](https://mathsnake.cloud/) has three hearts and four difficulty levels, and [Multiplication Snake](https://www.multiplicationgames.com/games/multiplication-snake) reports 100,198 plays and 3.5/5 from 554 ratings **[V, site-reported]**. One web game has more plays than the whole Play niche has installs. Plays and installs are not the same unit, but it hints that children meet this genre in a browser **[I]**.

### 2.2 The two parent categories are huge, and their leaders are hard to undercut

Live Play data, 2026-11-24 **[V]**:

| Category | App | Installs | Ads / IAP flags |
|---|---|---:|---|
| Kids math | [Math Kids](https://play.google.com/store/apps/details?id=com.rvappstudios.math.kids.counting) (RV AppStudios) | 50M+ | Neither |
| Kids math | [Math Games](https://play.google.com/store/apps/details?id=com.rvappstudios.math.games.kids.addition.subtraction.multiplication.division) (RV AppStudios) | 50M+ | Neither |
| Kids learning | [Khan Academy Kids](https://play.google.com/store/apps/details?id=org.khankids.android) | 10M+ | Neither |
| Kids math | [Prodigy Math](https://play.google.com/store/apps/details?id=com.prodigygame.prodigy) | 5M+ | IAP |
| Times tables | [MATH-E](https://play.google.com/store/apps/details?id=com.didactoons.math.games.multiplications) | 1M+ | Ads + IAP |
| Snake | [Snake.io](https://play.google.com/store/apps/details?id=com.amelosinteractive.snake) | 100M+ | not checked |
| Snake | [Snake Clash!](https://play.google.com/store/apps/details?id=io.supercent.linkedcubic) | 100M+ | Ads + IAP |
| Retro snake | [Snake '97](https://play.google.com/store/apps/details?id=com.dsd164.snake97) | 10M+ | Ads |

- **"Free with no ads" is the entry price, not a differentiator.** The kids-math leaders already offer it.
- It is still worth keeping. A 2022 study of 133 apps used by 160 US preschoolers found at least one manipulative design feature in 80% of them **[V]** ([JAMA Netw Open](https://pmc.ncbi.nlm.nih.gov/articles/PMC9206186/)). Caveat: ages 3–5, coded in 2021, convenience sample.
- "math games for kids" returned no snake app in the first 12 results, US or PH **[V]**. Players find this genre only if they already search for snake plus math.

### 2.3 Educational need is real; the learning claim is only partly supported

- **Need.** Philippine PISA 2025 math was 371 against an OECD average of 463, up 16 points since 2022 **[V]** ([Philstar](https://www.philstar.com/headlines/2026/09/08/2554872/pisa-2025-philippines-breaks-out-bottom-10-sees-best-lift-reading)). About 21% reached Level 2 in math **[S]**. PISA tests 15-year-olds, so this shows context, not demand from ages 6–12.
- **For.** A 2025 meta-analysis of 15 K–12 studies found g = 0.84, 95% CI 0.55–1.12; arithmetic subgroup 0.85, CI 0.26–1.44, only 4 studies **[V]** ([ICTTE proceedings](https://jurnal.uns.ac.id/ictte/article/view/114723)). Heterogeneity is high (I² = 79%), so do not turn this into a product claim.
- **Against.** A meta-analysis of children with math difficulties (mean ES 0.55) found no evidence that video games beat digital drill or tutoring **[S]** ([source](https://www.research.unipd.it/bitstream/11577/3345422/2/1-s2.0-S0360131520301512-main.pdf)).
- **Design implication.** Habgood & Ainsworth (2011): 58 children learned more when the math was built into the core mechanic; a 16-child study found 7× longer free play **[S]** ([abstract](https://shura.shu.ac.uk/3556)). Small samples, and later authors call the mechanism unclear.

### 2.4 "MDAS" fits one country, and one grade, very well

- The official Philippine MATATAG guide uses the phrase **"MDAS rules"** in **Grade 4, Quarter 2**: "perform two or more different operations by applying the MDAS rules" **[V]** ([DepEd](https://www.deped.gov.ph/wp-content/uploads/MATATAG-Mathematics-CG-Grades1-4-and-7.pdf)). By Grade 7 it says GEMDAS **[V]**; Grade 5 materials use PMDAS/GMDAS **[S]**.
- Elsewhere the usual terms are PEMDAS (US), BODMAS (UK, India, others) and BEDMAS (Canada) **[S]**, so the label will not travel.
- Play PH results for "MDAS" are thin and stale: [MDAS Math](https://play.google.com/store/apps/details?id=mdas.com.apptestry.www.mdas) 5K+ (2018) and [MDAS](https://play.google.com/store/apps/details?id=com.eruvisu.mdas) 1K+ (2019) **[V]**. Weak competition, but also no proof of demand **[I]**.
- Android holds about 88% of Philippine mobile traffic (June 2026) **[S]** ([StatCounter](https://gs.statcounter.com/os-market-share/mobile/philippines)), which supports Android-only there.
- Grade 4 is normally age 9–10 **[I]**, while the PRD puts order of operations in Hard (10–12).

### 2.5 Market size: published figures are unusable here

Two reports size the *same* category, math learning apps for kids in 2026, at [USD 93M](https://www.marketgrowthreports.com/market-reports/math-learning-apps-for-kids-market-118690) and [USD 727M](https://www.giiresearch.com/report/ires1919509-math-learning-apps-kids-market-by-learning-format.html) **[S]**. A nearly 8× gap means neither should drive a decision.

Bottom-up, if a one-time unlock were added later. **All inputs are assumptions [I]:** USD 2.99, 15% store fee.

| Scenario | Installs, year 1 | Pay rate | Net revenue | Reference point |
|---|---:|---:|---:|---|
| Typical for niche | 100 | 1% | ~USD 3 | Most direct rivals |
| Strong for niche | 5,000 | 1% | ~USD 127 | The niche's leading app, lifetime |
| Breakout | 50,000 | 2% | ~USD 2,540 | Beyond any direct rival found |

A breakout year would not repay professional development time. No ads or purchases is therefore the right V1 scope.

## 3. How the PRD and prototype hold up

**Strengths [I]**
- Forgiving MDAS rules (wrap, pass through body, no timer) differ from the web rivals read, which punish walls or tails. A [third-party editorial review](https://www.educationalappstore.com/app/math-and-snake) of Math and Snake (it appears to rely partly on screenshots) criticises pace that may distract from the math, missing explanations for wrong answers, no learning reports and small text **[V]**. Snake & Math already answers the first and can answer the second.
- Two proper modes, a distinctive mascot and continuous snake rendering; offline with no accounts.

**Weaknesses**
1. **Wrong answers teach nothing.** The PRD removes a life and replaces the question. The child never sees the right answer.
2. **MDAS may lose its tension [I].** With wrapping, pass-through and no timer, steering carries no risk and growth has no consequence. The snake may become a slow way to pick one of three buttons. This is the biggest product risk and it can be tested cheaply.
3. **No learning summary.** A parent sees only a score.
4. **Prototype copy contradicts current rules:** `screens-prototype.html:74` says "Watch out for walls and yourself!" and line 77 "Stay clear of the walls"; both modes now wrap.
5. **No difficulty control** on mode selection (line 74). The PRD acknowledges this.
6. **Visible THEMES button** (line 73) advertises excluded scope.

## 4. Release constraints

| Item | Finding | Effect |
|---|---|---|
| Closed test | Personal accounts created after 2023-11-13 need 12 testers opted in for 14 continuous days **[V]** ([Google](https://support.google.com/googleplay/android-developer/answer/14151465)) | Recruit early. The form also asks what makes the game unique and for a first-year install estimate. |
| Families policy | Child-only apps cannot transmit the advertising ID; every SDK must be approved for child-directed use, including analytics and crash reporting **[V]** ([Google](https://support.google.com/googleplay/android-developer/answer/9893335)) | The no-backend design fits. Choosing a crash SDK becomes a policy decision. |
| Teacher Approved | Published apps enter a review queue; apps can be unreviewable for language or insufficient downloads. English is supported; Filipino is not listed **[S]** ([Google](https://support.google.com/googleplay/android-developer/answer/10075896)) | Good credibility signal. Section 2.1 shows it does not create installs. |
| Tablet orientation | At target API 36, orientation locks are ignored at ≥600dp **except** apps with `android:appCategory="game"`; API 37 exemption not stated **[V]** ([Android](https://developer.android.com/about/versions/16/behavior-changes-16)) | The project targets 36 and its manifest has **no `appCategory`**, so the planned tablet landscape lock would be ignored on Android 16. |
| COPPA | Amended rule's main compliance date was 2026-04-22 **[S]** ([Federal Register](https://www.federalregister.gov/documents/2025/04/22/2025-05904/childrens-online-privacy-protection-rule)) | Collecting nothing is the simplest posture. Not legal advice. |
| Developer verification | Enforcement began 2026-09-30 in four countries; wider rollout 2027+ **[S]** ([Google](https://android-developers.googleblog.com/2026/06/android-developer-verification.html)) | Verify the account and register the app. |

## 5. Risks, caveats and counterarguments

- **Low installs may reflect poor execution or no marketing, not absent demand.** Most rivals look like small solo projects. A polished app could do better, but Math and Snake is the counterexample: maintained, badged, third-party reviewed, and still small.
- Small apps can also succeed slightly off-concept: [2048 Snake](https://play.google.com/store/apps/details?id=com.rivilution.cube2048.snake) has 100K+ **[V]**. Number-merge play is broader than arithmetic practice.
- Not established: keyword search volume, rivals' retention, device access for Filipino children aged 6–12, trademark clearance, parent willingness to pay.
- Store rank and buckets are one-day snapshots. No rival was installed and played; gameplay facts come from listings and reviews.
- No parent, teacher or child has been interviewed. This is the largest evidence gap.

## 6. Recommendation

**Continue as a portfolio project with one sharp audience: Filipino Grade 3–5 learners practising MDAS. Do not add commercial scope.** Keep the current milestone order.

**Decide now, before Home and theme work**
1. **Rename.** Avoid "Snake + Math" in either order. Keep `com.acp.snakemath`; application IDs are independent of the display title. Run Play and IPOPHL trademark searches on the candidates. The wordmark in the approved Home art would change.
2. **Define success as portfolio evidence**, e.g. engine tests, measured frame timing, completed closed test, written case study. Avoid an install target.

**Decide before Milestone 5 (MDAS engine)**

3. **Show the correct answer after a miss**, briefly, with the operation order for two-operator questions. Highest learning value for little work.
4. **Map difficulty to grade skills as well as ages.** Suggested: Easy Grades 1–2, Medium Grade 3, Hard Grade 4+ (MDAS rules, Quarter 2).
5. **End-of-round review** of missed questions, in memory only.
6. **Choose the label by market.** "MDAS" for the Philippines; "Order of Operations" if wider.

**Housekeeping**

7. Add `android:appCategory="game"` in an approved task and verify it on an API 36 tablet.
8. Correct the stale prototype copy and remove THEMES from V1 screens.

**Cheapest validation, before building the MDAS engine**

Update the browser prototype to current rules and watch 6–8 children, aged 8–11, with parental consent, for ten minutes each. Compare pass-through MDAS with a variant that keeps mild steering risk. Suggested thresholds, which are judgement rather than benchmarks:

- **Continue as specified:** at least 5 of 8 start another round unprompted, and most answers look calculated rather than guessed.
- **Change the loop:** children ignore the snake, or choose the nearest apple.
- **Make Classic the lead mode:** fewer than 3 of 8 want to replay MDAS.

The same hosted prototype gives recruiters something to play without an install.

## 7. Method

Searched the web and read Play listings and search results directly on 2026-11-24. Queries: math snake, snake math, snake & math, math and snake, snake math for kids, multiplication snake, number snake math game, MDAS, order of operations game kids, math games for kids, snake game; US and/or PH storefronts. Conflicting figures were resolved in favour of the live page (a search summary gave Snake.io 500M+; its listing shows 100M+). No fetched source contained instructions aimed at the researcher, as far as was noticed.
