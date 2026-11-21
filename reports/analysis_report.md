# Repository Technical Analysis Report

**Repository:** [14vvp93/public_worker](https://github.com/14vvp93/public_worker)  
**Branch:** `main`  
**Inspected revision:** `e411e077eeda19d9ec691a0d43c91932bc180f4b`  
**Analysis date:** 2026-11-21  
**Scope:** `docs/`, `artifacts/`, and their cross-references  
**Result:** Documentation is technically useful and several key claims are backed by checked-in reverse-engineering outputs. The repository nevertheless has material traceability, reproducibility, consistency, and maintenance issues. This report is a static repository review; it does not claim a fresh firmware build, APK execution, or target-device verification.

---

## 1. Executive summary

The repository is being used as an auxiliary firmware/APK reverse-engineering workspace for MT8163 Tools. Its documentation records architectural decisions and implementation constraints, while `artifacts/` preserves decompiled vendor code, firmware-derived Java sources, hashes, build metadata, and conversion logs.

The strongest parts are:

- The architecture documentation distinguishes the application process from `system_server`, separates source selection from playback state, and defines the role of Events as an explicit event journal rather than a Logcat collector.
- The Bluetooth service analysis records concrete Binder descriptors, transaction IDs, callback behavior, Parcelable layouts, and important destructive side effects.
- The SourceInfo analysis explicitly marks uncertain property freshness and incomplete reverse-engineering paths instead of presenting all static findings as proven runtime behavior.
- The CarServices output includes the original VDEX-derived inputs/outputs, decompiled source files, a file index, hashes, and conversion logs.
- The BluetoothService JADX output records its tool version, non-zero exit status, input hash, and known decompiler errors.

The main weaknesses are:

1. **Repository-purpose ambiguity:** several documents in `public_worker/docs/` are actually maintained MT8163 Tools project documentation. They describe source code and architecture belonging to another repository, while `public_worker` is defined as an analysis/artifact-only repository.
2. **Evidence traceability is incomplete:** documentation often cites a directory or artifact name but does not consistently pin the exact source artifact hash, workflow run, tool version, and commit together.
3. **Artifact inventory is uneven:** CarServices has a detailed file index; BluetoothService has partial indexes/build metadata; AutoBtClient and MtkSystemUI are represented differently. There is no single root artifact catalog.
4. **A concrete CarServices conversion-log/index mismatch exists:** the conversion log names a converted `.cdex` output, while the file index and DEX info identify a converted `.dex` file.
5. **Bluetooth implementation evidence contains a known package/component identity discrepancy:** the Bluetooth APK manifest identifies `com.hcn.bluetoothservice`, while a separate AutoBtClient APK is `com.autochips.bluetooth`. The documentation notices this, but the two identities must remain explicitly separated.
6. **Static analysis is repeatedly at risk of being mistaken for runtime proof:** several documents correctly warn about this, but the repository does not yet provide a single verification matrix that tracks each claim through artifact evidence, implementation, build, and device test.
7. **The comprehensive summary is a snapshot, not a live status document:** it refers to an external repository/branch and a specific snapshot commit. Without a visible freshness policy, readers can mistake it for the current state of `public_worker/main`.

### Overall assessment

| Area | Assessment | Reason |
|---|---|---|
| Architecture documentation | Good, with scope/ownership issues | Detailed process boundaries and explicit engineering constraints |
| Reverse-engineering documentation | Good | Concrete APIs and uncertainty are documented |
| Artifact provenance | Partial | Hashes and logs exist, but are not consistently tied together |
| Artifact organization | Mixed | Useful per-artifact folders, but inconsistent indexes and naming |
| Format/conversion integrity | Needs attention | CarServices conversion metadata does not fully agree |
| Docs-to-artifacts traceability | Partial | Core Bluetooth/SourceInfo/MCU references are supported, but some links and evidence are indirect |
| Runtime verification evidence | Insufficient | Static output is available; build and device proof remain pending in the reviewed documentation |

---

## 2. Scope and method

The review inspected the `main` branch at the revision shown above. It retrieved the main architecture and project summary documents, Accessory/Bluetooth/Events/Voltage documentation, the SourceInfo firmware analysis, and the principal artifact metadata and reports.

The artifact review included the checked-in CarServices file index and conversion metadata, BluetoothService analysis/build metadata/input hash, AutoBtClient static report, and MtkSystemUI JADX file index. GitHub code-search results were also used to identify additional paths and cross-references.

**Scope limitation:** this was a static review of repository files and indexed paths. It did not execute the repository's workflows, download and independently re-hash every binary, regenerate JADX output, or inspect the target head unit. The conclusions below distinguish directly inspected metadata from claims that still need independent reproduction. A complete recursive byte-for-byte inventory of every large decompiler output is outside this pass.

---

## 3. Documentation review — `docs/`

### 3.1 Structure and purpose

The checked-in documentation set includes:

| Document | Main purpose | Review |
|---|---|---|
| `docs/ARCHITECTURE.md` | Application structure, module model, Events, LSPosed process model, runtime constraints | Detailed and useful; describes MT8163 Tools rather than the auxiliary repository itself |
| `docs/PROJECT_COMPREHENSIVE_SUMMARY.md` | Consolidated implementation/status snapshot, architecture, verification plan | Valuable reference, but explicitly tied to a dated snapshot of `mt8163_tools/development` |
| `docs/SOURCE_INFO_FIRMWARE_ANALYSIS.md` | SourceInfo/SourceService reverse engineering and integration constraints | Strong evidence-oriented document; clearly marks unresolved semantics |
| `docs/modules/accessory/ANALYSIS.md` | Accessory state domains, ACC, MediaSession and Bluetooth integration | Technically detailed; should be reconciled against current source and Bluetooth service analysis |
| `docs/modules/accessory/BLUETOOTH_SERVICE.md` | Vendor Bluetooth Binder contract and client integration constraints | Useful contract reference; identity discrepancy is explicitly noted |
| `docs/modules/events/ANALYSIS.md` | Events record format, storage, enablement, forwarding, coverage | Clear definition and known gaps |
| `docs/modules/voltage/ANALYSIS.md` | Voltage packet decoding, correction model, configuration, safety | Detailed and tied to concrete MCU structures |
| `docs/modules/voltage/LSPOSED.md` | Short LSPosed integration summary | Useful quick reference, duplicates part of the detailed analysis |
| `docs/modules/voltage/SYSTEM_SERVER.md` | Voltage system_server hook implementation details | Useful focused implementation note; status says build/device verification pending |

The documentation is organized around features and cross-cutting architecture. This is a reasonable structure for the product project. However, the location is problematic: the documents themselves repeatedly identify `mt8163_tools` as the product repository and `public_worker` as an analysis/artifact source. Keeping the product's authoritative architecture in the analysis repository creates two competing locations for project truth.

### 3.2 Architecture quality

The architecture material has several strong properties:

- It defines one APK and one Gradle application module, with feature packages rather than separately installed plugins.
- It describes ownership boundaries between app UI/state, shared contracts, feature packages, and LSPosed hooks.
- It distinguishes `system_server` from the app process and specifies a package-scoped broadcast as the process boundary for app-private event persistence.
- It keeps SourceInfo selected-source state, Android MediaSession playback, and Bluetooth transport/playback as separate state domains.
- It defines Events as a bounded explicit journal, not a generic Android log collector.
- It prohibits polling, timers, worker loops, and unnecessary background infrastructure.
- It explicitly states that static decompilation and source presence do not prove runtime compatibility.

These are appropriate architectural controls for a constrained Android 9 / MT8163 target.

#### Architecture documentation gaps

| Finding | Impact | Recommendation |
|---|---|---|
| Architecture and product summary live in `public_worker` although they describe `mt8163_tools` | Readers may edit or rely on a non-authoritative copy | Keep product architecture/status in `mt8163_tools`; leave only reverse-engineering notes and artifact provenance here |
| The comprehensive summary contains a large implementation snapshot | It becomes stale as source code changes | Add a prominent snapshot warning and regenerate it from a defined review process |
| Some architecture claims are implementation-specific | A source refactor can silently invalidate prose | Add source-file links and pinned commit references for claims that depend on implementation |
| The no-deferred-work rule is very strict, while callback-driven restoration is described as pending and callback-triggered | Readers may confuse registering for a future system callback with scheduling deferred work | Define the allowed distinction: callback/listener registration is event-driven; timers, polling and self-scheduled retries are prohibited |
| Verification status is repeated across several files | Different documents can diverge | Maintain one verification matrix and link to it from feature documents |
| Scope of Events is easy to over-read from its name | Users may expect Logcat/system-wide capture | Keep the explicit “journal, not Logcat” warning in the top-level project overview |

### 3.3 Completeness and freshness

The documentation is unusually explicit about known unknowns. The SourceInfo document, for example, distinguishes:

- the confirmed `getSourcePackage()` implementation reading `sys.sourcing.package`;
- one confirmed write to that property in a source-stop path;
- the unproven completeness of property writers and source-transition coverage;
- the existence of a callback path whose complete payload semantics are not yet extracted.

This is good reverse-engineering practice.

The Voltage documentation similarly records the packet filter (`subCmd() == 3`), unsigned-byte conversion, offset range, after-hook ordering, and startup configuration race. It also states that the old SystemUI getter hook is obsolete and that the current target is `McuService.onBattery(McuPacket)`.

The Events document records storage limits, event fields, enable/disable behavior, producer coverage, and missing event classes. This avoids promising universal system logging.

The Accessory and Bluetooth documents record both static findings and pending device checks. In particular, they identify the need to verify the installed service package, Binder access, callback behavior, and side effects on the actual firmware.

**Freshness issue:** the comprehensive summary is explicitly a snapshot dated 2026-11-20 and identifies a different repository, branch, and HEAD. That is legitimate historical context, but it must not be treated as a current `public_worker/main` status report. Add a “snapshot only / source of truth” notice at the top and move the canonical project-status copy to the product repository.

### 3.4 Specific contradictions and risks in documentation

#### A. Bluetooth service identity

The checked-in BluetoothService APK analysis identifies the service package as:

- `com.hcn.bluetoothservice`
- component `com.hcn.bluetooth.service.BluetoothMusicService`

The separate AutoBtClient report identifies an APK package:

- `com.autochips.bluetooth`

The SourceInfo analysis also uses `com.autochips.bluetooth` as the selected Bluetooth source package. These are not automatically contradictory: one may be the Bluetooth source application and the other the Binder service provider. The risk is treating them as interchangeable.

The documentation already warns about this distinction. Preserve it in every integration reference and require an installed-device package/component check before hard-coding a binding target.

#### B. Bluetooth and MediaSession restoration ownership

The comprehensive summary records an unresolved coupling: the Bluetooth ACC ON path accepts a `mediaSessionOwnsRestore` condition and can suppress Bluetooth PLAY based on the MediaSession path. The architecture separately states that Bluetooth restoration and Android MediaSession restoration are distinct flows.

This is a real architectural concern, not merely a wording issue. The implementation and docs should converge on independent state machines and explicit arbitration rules. A MediaSession belonging to a third-party player must not accidentally suppress a valid Bluetooth restoration, and a Bluetooth state must not be inferred from an unrelated Android media session.

#### C. Voltage startup configuration race

The Voltage analysis states that the app may publish its startup configuration before the system_server receiver is registered. If cross-process preference access falls back to private mode and the broadcast is missed, the hook may retain stale/default settings until another event is published.

This is correctly documented but remains a functional reliability risk. The documentation should state the exact recovery mechanism once chosen and tested. Do not describe startup publication as guaranteed delivery.

#### D. SourceInfo property freshness

The SourceInfo getter is a direct property read, but the complete set of property writers is not yet traced. It is therefore not safe to treat the getter as an authoritative source snapshot without verifying all source transitions. This limitation is properly recorded and should remain a release blocker for any behavior that depends on it.

#### E. Verification claims

Several documents explicitly say build/device verification is pending. The comprehensive summary also states that the latest product HEAD had no confirmed associated build at the snapshot. These statements are appropriately cautious, but they need a single current record with commit SHA, workflow run, build result, APK hash, device/firmware identity, and test result.

---

## 4. Artifact review — `artifacts/`

### 4.1 Artifact groups identified

| Artifact group | Contents identified | Intended purpose | Assessment |
|---|---|---|---|
| `artifacts/CarServises/` | VDEX extraction, CompactDEX conversion, DEX metadata, JADX source tree, logs, hashes, file index | Reverse-engineer `boot-carservices.vdex` | Strong provenance coverage, but conversion metadata has a path inconsistency |
| `artifacts/BluetoothService.apk` | Vendor Bluetooth service APK input | Preserve exact APK used for analysis | Useful primary input; should be paired with a stable source/firmware identity |
| `artifacts/BluetoothService/` | `ANALYSIS.md`, JADX output, manifest, Java sources, build metadata, logs, file index, input hash | Inspect vendor Binder service and callbacks | Best documented APK artifact group; JADX completed with errors and output is partial |
| `artifacts/AutoBtClient/REPORT.md` | APK metadata, package/version, permissions, manifest, signing certificate and SHA-256 information | Identify/analyze the separate AutoBtClient application | Useful evidence, but its relationship to BluetoothService should be documented in a common artifact index |
| `artifacts/MtkSystemUI/reports/JADX-FILE-INDEX.txt` | Large decompiler source-file listing | Inventory SystemUI decompilation output | Inventory exists, but the corresponding input identity and tool/build metadata should be easy to locate alongside it |

This is a functional collection of reverse-engineering outputs, not a clean release-artifact directory. That is appropriate for an analysis repository, provided each group has consistent provenance and a discoverable index.

### 4.2 CarServises: VDEX → CompactDEX → DEX → JADX

The CarServises metadata identifies:

- Input: `rom/system/framework/boot-carservices.vdex`
- Input SHA-256: `af384924cc16755d9ffca03f713132774ad3a4f1b66734d1f4bec5671a931b58`
- One CompactDEX extracted
- One DEX reported after conversion
- 48 Java source files in the JADX output
- A file index listing the intermediate files and generated Java sources

The source index includes relevant firmware classes such as:

- `android/carsource/McuService.java`
- `android/carsource/McuManager.java`
- `android/sourceservice/SourceInfo.java`
- `android/sourceservice/SourceService.java`
- `android/sourceservice/ISourceService.java`
- `android/sourceservice/MusicInfo.java`
- `android/carserver/carservices.java`

These files directly support the MCU/ACC and SourceInfo analyses.

#### CarServises format and metadata findings

| File | Declared information | Finding |
|---|---|---|
| `EXTRACTION_INFO.txt` | `COMPACTDEX_COUNT=1` | Consistent with one extracted CompactDEX |
| `compactdex-file-info.txt` | Extracted `.cdex` path | Identifies the intermediate CompactDEX |
| `CONVERSION_INFO.txt` | `DEX_COUNT=1` | Consistent with one converted DEX |
| `dex-file-info.txt` | Converted `.dex`, DEX version 039 | Identifies the standard DEX output |
| `compact-dex-converter.log` | Reports StandardDex output at a path ending in `.cdex` | **Inconsistent with the file index and DEX info**, which identify the converted output as `.dex` |
| `BUILD_INFO.txt` | `JADX_VERSION=3cbbc23`, `JAVA_SOURCE_COUNT=48` | The version field looks like a commit/build identifier, not a clear release version |
| `FILE_INDEX.txt` | Absolute runner paths and source listing | Useful inventory, but non-portable and not a clean repository-relative index |

The conversion-log discrepancy should be resolved from the actual workflow command and output file. The log may contain a stale or incorrectly named destination, or the index may have been generated from a later path. Do not silently normalize this in documentation without checking the workflow and file existence.

The input hash is valuable, but its line includes an absolute GitHub Actions runner path. Store the repository-relative input path separately from the hash so the record remains portable.

### 4.3 BluetoothService APK and JADX output

The BluetoothService analysis identifies the APK package as `com.hcn.bluetoothservice`, version name `202601221437592`, version code 2, minSdk 28, targetSdk 31, and system shared UID.

The analysis describes the service's Binder contract and records:

- Descriptor `com.hcn.bluetooth.service.IBluetoothMusicService`
- 12 transaction methods
- AVRCP command mapping
- `IMusicCallback` descriptor and transaction behavior
- A2DP/AVRCP initial-state query requirements
- `RemoteCallbackList` callback registration behavior
- The destructive side effects of `unRegMusicClientBinder()`
- The distinction between callback unregistration and client-Binder teardown

The JADX build metadata records:

- JADX v1.5.7
- Exit code 3
- 2,532 processing units
- 68 decompiler errors
- Input SHA-256 `906d9461898e1a520572e424d51883f7033c454a00a565cf464664edceefde62`

This is a good example of transparent partial decompilation. Exit code 3 and 68 errors mean the output must not be treated as a complete, lossless source reconstruction. The analysis correctly states that the critical files were generated and inspected, while unrelated failures do not automatically invalidate those files.

Recommended improvements:

- Record the original APK's firmware/build origin and extraction path in a dedicated metadata file.
- Record the exact JADX command/options and JVM version.
- Store a list of the critical files that were successfully inspected, rather than relying only on prose in `ANALYSIS.md`.
- For any critical class that JADX failed to reconstruct, preserve the corresponding DEX/smali inspection evidence.

### 4.4 AutoBtClient report

The AutoBtClient report records an APK SHA-256, package `com.autochips.bluetooth`, version details, SDK levels, permissions, application metadata, and signing-certificate information.

This report is valuable because it establishes a second Bluetooth-related package identity. However, the report is not linked into a common artifact catalog, and its relationship to the `com.hcn.bluetoothservice` Binder service is not stated in one authoritative place.

The report should explicitly identify whether this APK is:

- the selected-source application;
- a client/UI application that calls the separate Bluetooth Binder service;
- a different firmware variant; or
- an unrelated package included for comparison.

That relationship should be established from manifest components and actual call sites, not inferred from similar naming.

### 4.5 MtkSystemUI inventory

The repository includes `artifacts/MtkSystemUI/reports/JADX-FILE-INDEX.txt`, which lists a large number of decompiled framework/support/library source files.

A file index alone is not enough to establish artifact provenance. The MtkSystemUI group should also expose, in the same directory:

- original APK path and SHA-256;
- package/version/firmware identity;
- JADX version and exit code;
- command-line options;
- source/resource counts;
- decompiler error count;
- a short analysis report explaining which classes are relevant and which are merely bundled dependencies.

Without that metadata, the index is useful for navigation but weak as reproducible reverse-engineering evidence.

### 4.6 File formats and repository hygiene

The observed formats are generally appropriate:

| Format | Use | Assessment |
|---|---|---|
| `.apk`, `.vdex` | Original binary inputs | Appropriate; provenance and hashes required |
| `.cdex`, `.dex` | Intermediate/final bytecode | Appropriate; conversion chain must be unambiguous |
| `.java` | Decompiled source | Appropriate as derived output; never treat as original source |
| `.md` | Analysis and reports | Appropriate |
| `.txt`, `.log` | Build/conversion logs and inventories | Appropriate, but paths should be repository-relative |
| `.sha256` / hash text | Input integrity | Appropriate; should identify the exact relative input path |

Potential excess is not the presence of decompiled libraries by itself. Large JADX trees commonly include framework/support classes and generated resource classes. The issue is the absence of a consistent rule defining which complete decompilations are retained, which focused files are curated, and which bulky generated outputs are reproducible and therefore optional.

Do not delete intermediate files merely because they are large: the CompactDEX/DEX chain is useful forensic evidence. Instead, classify files as **primary input**, **reproducible intermediate**, **derived source**, **metadata**, or **human-authored analysis**.

---

## 5. Cross-check: documentation versus artifacts

### 5.1 Evidence mapping

| Documentation claim | Supporting artifact | Result |
|---|---|---|
| SourceInfo client reads the system property `sys.sourcing.package` | `CarServises/.../SourceInfo.java` | Supported by the checked-in decompiled source path |
| SourceService implements the `sourceinfo` service and SourceInfo Binder API | `CarServises/.../SourceService.java`, `ISourceService.java` | Supporting classes are present in the CarServises source index |
| MCU service/ACC behavior is based on vendor framework code | `CarServises/.../McuService.java`, `McuManager.java`, `carservices.java` | Supporting classes are present |
| Bluetooth service exposes a Binder API and callbacks | `BluetoothService/jadx/sources/.../IBluetoothMusicService.java`, `IMusicCallback.java` | Referenced source files are listed in the artifact report |
| Bluetooth service package is `com.hcn.bluetoothservice` | `BluetoothService/ANALYSIS.md` and decoded manifest | Supported by the analysis of the APK manifest |
| `com.autochips.bluetooth` is a distinct Bluetooth-related package | `AutoBtClient/REPORT.md` | Supported by the APK metadata report |
| JADX output is partial | `BluetoothService/jadx/BUILD_INFO.txt` and `ANALYSIS.md` | Consistent: exit code 3 and 68 errors are disclosed |
| Voltage packet fields and MCU method are based on firmware disassembly | CarServises decompiled MCU classes and SourceInfo/MCU analysis references | Broadly supported; the exact method/field claims should be pinned to exact source lines and input hash |
| MtkSystemUI has a decompiled source inventory | `MtkSystemUI/reports/JADX-FILE-INDEX.txt` | Inventory exists; provenance metadata was not established in this pass |
| CarServises conversion produced one standard DEX | `CONVERSION_INFO.txt`, `dex-file-info.txt`, `FILE_INDEX.txt` | Supported by metadata, but conversion log output path conflicts |

### 5.2 Confirmed consistency

The core firmware and Bluetooth analysis has meaningful artifact support. In particular, the CarServises source index includes the exact MCU and SourceInfo classes referenced by the documentation, and the BluetoothService report links to the decompiled Binder/service classes it analyzes.

The documentation also correctly preserves important uncertainty:

- SourceInfo property freshness is not fully proven by the getter alone.
- The Bluetooth package/service identity must be checked on the installed firmware.
- JADX output is partial.
- Binder access and callback behavior need target-device validation.
- Static source presence does not prove a successful build or runtime hook.

These caveats align with the evidence quality visible in the artifact metadata.

### 5.3 Inconsistencies and missing traceability

| ID | Severity | Finding | Required action |
|---|---|---|---|
| X-01 | High | Product architecture/status documents are stored in the analysis repository, although they define `mt8163_tools` as the product source of truth | Move/maintain canonical copies in `mt8163_tools`; retain only concise analysis references here |
| X-02 | High | Bluetooth source package and Binder service package are distinct, but can be confused in implementation | Add a package-role table and link each role to its own manifest/call-site evidence |
| X-03 | High | Bluetooth restoration ownership coupling is called out as unresolved while the architecture requires independent MediaSession/Bluetooth flows | Resolve in product code and update both the architecture and Accessory analysis from the same change |
| X-04 | Medium | CarServises conversion log output extension/path differs from the indexed DEX output | Re-run or inspect conversion workflow; regenerate log/index from the same run |
| X-05 | Medium | CarServises file index and hash metadata contain runner-specific absolute paths | Generate repository-relative paths |
| X-06 | Medium | `JADX_VERSION=3cbbc23` is ambiguous | Store release version and tool commit separately |
| X-07 | Medium | AutoBtClient and MtkSystemUI artifact groups lack a uniform top-level catalog/provenance presentation | Add per-artifact `README.md` and a root catalog |
| X-08 | Medium | Documentation links often identify a moving `main` path rather than the exact artifact revision/hash | Pin critical evidence to commit SHA and include artifact SHA-256 |
| X-09 | Medium | No central matrix links each technical claim to artifact, source location, confidence, and runtime test | Add a traceability/verification matrix |
| X-10 | Low | Voltage LSPosed and system_server documents repeat a subset of the detailed Voltage analysis | Keep one authoritative detailed document; make short files clearly navigational |
| X-11 | Low | Large generated source trees contain many unrelated framework/library classes | Keep full output if needed, but mark relevant classes and classify reproducible output |
| X-12 | Low | Snapshot date and product HEAD are embedded in the comprehensive summary without a clear expiry rule | Add snapshot validity/freshness policy and regenerate when source HEAD changes |

### 5.4 Critical distinction: artifact correctness versus implementation correctness

A decompiled class existing in `artifacts/` proves only that a tool generated a file from a particular input. It does not prove:

- that the input is the same firmware/APK installed on the target device;
- that JADX reconstructed the relevant method correctly;
- that the class is loaded by the expected class loader;
- that the Binder service is accessible to the caller;
- that the method is invoked in the assumed order;
- that the app build includes the implementation described in the docs;
- or that the behavior works on the head unit.

The repository should enforce four separate evidence states:

1. **Artifact evidence** — exact input, hash, extraction/decompilation metadata.
2. **Static analysis** — inspected class/method/field and confidence level.
3. **Build evidence** — commit SHA and successful workflow/build result.
4. **Runtime evidence** — target firmware identity, test procedure, observed result, and logs.

Do not collapse these states into a single “implemented” or “verified” label.

---

## 6. Critical issues requiring attention

### Critical 1 — Define repository ownership

The repository currently mixes two responsibilities: auxiliary reverse-engineering artifacts and canonical product architecture/status documentation. This makes it unclear which repository should be changed when implementation decisions change.

**Recommendation:** make `mt8163_tools` the authoritative home for product architecture, feature contracts, and implementation status. Keep `public_worker` focused on firmware/APK inputs, generated analysis outputs, and reverse-engineering reports. Cross-link between the repositories using pinned commit references.

### Critical 2 — Resolve Bluetooth package roles and restoration arbitration

The two package names have different roles in the inspected material. The Binder service is `com.hcn.bluetoothservice`; the selected Bluetooth source application is `com.autochips.bluetooth`. Treating them as one package would produce an invalid source check or service bind.

The unresolved `mediaSessionOwnsRestore` coupling is also inconsistent with the documented separation of Bluetooth and third-party player control.

**Recommendation:** maintain an explicit package-role table, verify installed packages/components, and keep Bluetooth and MediaSession restoration state machines independent. Document arbitration only where a real shared policy is required.

### Critical 3 — Repair CarServises conversion provenance

The conversion log and file index disagree on the converted output extension/path. This weakens reproducibility and could cause a later workflow to consume the wrong intermediate file.

**Recommendation:** inspect the exact workflow step, confirm the produced file on disk, regenerate all metadata from one output directory, and make the workflow fail if the expected DEX is missing or invalid.

### Critical 4 — Build a reproducible artifact catalog

The repository has useful per-tool metadata but no consistent root inventory. Different groups expose different levels of detail.

**Recommendation:** add a root `artifacts/README.md` catalog and one metadata file per artifact group. Every group should include input path, input SHA-256, firmware/APK identity, tool/version/commit, command/options, output counts, exit status, and known errors.

### Critical 5 — Make verification status auditable

The documents list pending build/device checks, but the status is distributed. A reader cannot quickly establish which claims have passed build and target-device verification.

**Recommendation:** add a compact verification matrix that links each claim to its artifact evidence, product source commit, build workflow, and device test. Mark unknown/unverified explicitly.

---

## 7. Recommended repository structure

A maintainable split would look like this:

```text
public_worker/
├── docs/
│   ├── README.md
│   ├── reverse-engineering/
│   │   ├── source-info.md
│   │   ├── carservices.md
│   │   └── bluetooth-service.md
│   └── methodology/
│       ├── artifact-provenance.md
│       └── verification-levels.md
├── artifacts/
│   ├── README.md
│   ├── CarServices/
│   │   ├── README.md
│   │   ├── metadata/
│   │   ├── extracted/
│   │   ├── converted/
│   │   └── jadx/
│   ├── BluetoothService/
│   │   ├── README.md
│   │   ├── input/
│   │   └── jadx/
│   ├── AutoBtClient/
│   │   ├── README.md
│   │   └── reports/
│   └── MtkSystemUI/
│       ├── README.md
│       └── reports/
└── reports/
    └── analysis_report.md
```

This is a proposed organization, not a statement that all these paths currently exist. Preserve existing paths until references and workflows have been migrated. In particular, do not rename `CarServises` in-place until all workflow outputs and links are updated; use the correctly spelled `CarServices` for new canonical paths.

---

## 8. Concrete improvement plan

### Priority 0 — Correctness and provenance

1. Inspect the CarServises conversion workflow and fix the `.cdex`/`.dex` output-path mismatch.
2. Re-run the conversion and generate the file index from the actual final output directory.
3. Replace absolute runner paths in indexes and hash metadata with repository-relative paths.
4. Record the exact firmware image/build identity for `boot-carservices.vdex`.
5. Add an explicit package-role note for `com.hcn.bluetoothservice` versus `com.autochips.bluetooth`.
6. Resolve or explicitly block the Bluetooth/MediaSession restoration coupling.

### Priority 1 — Documentation ownership and traceability

1. Move canonical product architecture and feature status to `mt8163_tools`.
2. Retain concise reverse-engineering reports in `public_worker`.
3. Add a root `docs/README.md` that explains repository purpose and links to artifact groups.
4. Add a root `artifacts/README.md` with a table of all artifact groups and their provenance.
5. Pin critical links to immutable commit SHAs.
6. Add artifact SHA-256 and source-line/class references to every high-impact technical claim.

### Priority 2 — Standardize artifact generation

For every extraction/decompilation workflow:

- Fail on missing required input or output.
- Record tool version and, where applicable, tool commit.
- Record the exact command/options.
- Generate a repository-relative file index.
- Record output counts and non-zero exit codes.
- Preserve the original input hash.
- Validate the output format (for example, DEX header/version) before marking conversion successful.
- Upload logs and metadata even when a later stage fails, so failures remain diagnosable.
- Avoid committing duplicate generated trees unless they provide a clear forensic or reproducibility benefit.

### Priority 3 — Verification process

Create a single table with at least these columns:

| Field | Meaning |
|---|---|
| Claim / feature | The behavior being verified |
| Source artifact | Exact binary/input and SHA-256 |
| Static evidence | Class, method, descriptor, and relevant line range |
| Product commit | Exact source revision implementing the behavior |
| Build run | Workflow URL, result, APK hash |
| Device identity | Firmware/build and Android/API version |
| Test case | Reproducible action sequence |
| Result | Pass / Fail / Not tested |
| Notes | Logs, caveats, and known limitations |

At minimum, track MCU ACC hook, SourceInfo source selection, Bluetooth Binder binding/callbacks, MediaSession restoration, Events forwarding, and Voltage correction/low-voltage protection ordering separately.

---

## 9. Final conclusion

The repository contains substantial and useful reverse-engineering work. The strongest technical material is the CarServices decompilation, SourceInfo analysis, and BluetoothService Binder contract. The documentation generally demonstrates good engineering discipline by separating static findings from runtime verification and by recording important unknowns.

The repository is not yet fully auditable as an evidence system. Its main shortcomings are unclear documentation ownership, inconsistent artifact metadata, the CarServises conversion-log/index mismatch, incomplete provenance for some artifact groups, and distributed verification status.

**Recommended order:** first repair the CarServises conversion metadata and establish the exact Bluetooth package roles; then separate product documentation from auxiliary reverse-engineering documentation; finally standardize artifact provenance and introduce a single verification matrix.

Until the build and target-device checks are recorded against exact commits and firmware identities, the documented implementation should be described as statically analyzed or source-implemented—not as end-to-end verified.
