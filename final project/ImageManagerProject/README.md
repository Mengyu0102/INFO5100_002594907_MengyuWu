# ImageManagerProject

**Course:** INFO 5100
**Due date:** Dec 13, 2025 9:00 AM PST

## What this project provides
- JavaFX GUI to upload image files from your desktop.
- Shows thumbnails sized **100x100** in a tile grid.
- Displays basic image properties (width, height) and EXIF metadata when available.
- Convert images to common formats (PNG, JPG, BMP) using Strategy pattern.
- Simple filters: Grayscale and Tint (as Filter strategies).
- Exception handling and user-friendly alerts.
- Well-commented source code.
- UML (PlantUML) class diagram included.
- Instructions to run using Maven.

## How to run
1. Make sure you have Java 17+ and Maven installed.
2. From project root, run:
```bash
mvn javafx:run
```
3. The application window will open. Use **Upload** to select images, click thumbnails to view properties, use **Convert** and **Download**.

## Files included
- `src/main/java/edu/example/app/*` - Java source files.
- `pom.xml` - Maven project file.
- `plantuml/classdiagram.puml` - PlantUML source for class diagram.
- `screenshots/` - placeholder screenshots (replace with your own test screenshots).
- `README.md` - this file.

## GitHub
- Initialize a git repo and push. If private, invite instructor and TA as viewers.

## Notes on grading checklist
- Thumbnails are created at 100x100 (required).
- Inheritance, Encapsulation, Interfaces present.
- Uses Strategy pattern for conversion and filters; Config uses Singleton.
- Code contains comments and exception handling.

