#!/bin/bash

# 인자로 숫자를 받아서 사용
if [ -z "$1" ]; then
  echo "Error: You must provide a number as an argument."
  exit 1
fi

COUNT=$1

# 1. 변경 사항 스테이징 및 커밋
git add .
git commit -m "githubaction test${COUNT}"

# 2. 최신 원격 브랜치 가져오기
git fetch origin

# 3. 로컬 브랜치와 원격 브랜치 병합
git merge origin/main  # 원격의 main 브랜치와 병합

# 4. 원격 저장소로 푸시
git push origin main

# 5. PR 생성
gh pr create --repo HowAboutThere/HowAboutThere-BE --base main --head HyunJunSon:main --title "githubaction test${COUNT}" --body "githubaction test${COUNT}"
