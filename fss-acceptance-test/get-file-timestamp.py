
# Usage: python get-file-timestamp.py <filename>
import sys
from datetime import datetime

file_path = sys.argv[1]
# read file timestamp
import os.path, time
file_stats = os.stat(file_path)

# Reading timestamps
modification_time = datetime.fromtimestamp(file_stats.st_mtime)
formatted_timestamp = modification_time.strftime("%Y-%m-%d-%H-%M-%S")
print(formatted_timestamp)

