import os

# Define the root folder name you're searching for
ROOT_FOLDER_NAME = "AutomationExercisee"
OUTPUT_FILE = "aggregated_code.txt"
VALID_EXTENSIONS = (".java", ".xml", ".properties", ".txt", ".yml")

def find_root_dir(start_path="."):
    abs_start = os.path.abspath(start_path)
    if os.path.basename(abs_start) == ROOT_FOLDER_NAME:
        return abs_start
    for root, dirs, _ in os.walk(start_path):
        if ROOT_FOLDER_NAME in dirs:
            return os.path.join(root, ROOT_FOLDER_NAME)
    raise FileNotFoundError(f"Root folder '{ROOT_FOLDER_NAME}' not found.")

def aggregate_code(root_dir):
    with open(OUTPUT_FILE, "w", encoding="utf-8") as output:
        for subdir, _, files in os.walk(root_dir):
            for file in files:
                if file.endswith(VALID_EXTENSIONS):
                    file_path = os.path.join(subdir, file)
                    relative_path = os.path.relpath(file_path, root_dir)
                    output.write(f"\n{'-'*80}\nFILE: {relative_path}\n{'-'*80}\n\n")
                    try:
                        with open(file_path, "r", encoding="utf-8") as f:
                            content = f.read()
                            output.write(content)
                    except Exception as e:
                        output.write(f"[Error reading file: {e}]\n")
                    output.write("\n\n\n")  # Spacing between files

if __name__ == "__main__":
    try:
        print("🔍 Searching for project root...")
        root_directory = find_root_dir(".")
        print(f"✅ Found root at: {root_directory}")
        print("📦 Aggregating code files...")
        aggregate_code(root_directory)
        print(f"✅ Done! Output written to: {OUTPUT_FILE}")
    except Exception as e:
        print(f"❌ Error: {e}")
