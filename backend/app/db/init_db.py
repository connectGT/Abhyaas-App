import os
import sys
import json
from app.db.session import SessionLocal, engine, Base
from app.models.test import TestSeries, TestSeriesFolder, Test, TestSection
from app.models.question import Question, Option

def init_db():
    Base.metadata.create_all(bind=engine)
    db = SessionLocal()
    
    try:
        if db.query(Question).count() == 0:
            data_path = os.path.join(os.path.dirname(os.path.dirname(os.path.dirname(__file__))), "scripts", "cleaned_test_data.json")
            with open(data_path, "r", encoding="utf-8") as f:
                data = json.load(f)
                
            series = TestSeries(
                id="nayab_tehsildar_2026",
                title=data["metadata"]["title"],
                subtitle=" | ".join(data["metadata"]["syllabus"]),
                category_id="nayab_tehsildar",
                total_tests=1,
                full_tests_count=1
            )
            db.add(series)
            
            folder = TestSeriesFolder(
                id="teh_f1",
                series_id=series.id,
                title="Paper 1 & 2: Full Mock Tests",
                folder_type="MOCK"
            )
            db.add(folder)
            
            test = Test(
                id="nayab_tehsildar_2026_full_mock_test_01",
                series_id=series.id,
                folder_id=folder.id,
                title="Full Mock Test - 01",
                sub_category="Exam Day Special",
                duration_minutes=150,
                total_questions=200,
                total_marks=400.0,
                is_free=True
            )
            db.add(test)
            db.commit()
            
            section_map = {}
            for q_data in data["questions"]:
                sec_name = q_data["section_name"]
                if sec_name not in section_map:
                    sec = TestSection(
                        id=f"sec_{len(section_map) + 1}",
                        test_id=test.id,
                        name=sec_name
                    )
                    db.add(sec)
                    db.commit()
                    section_map[sec_name] = sec.id
                    
            for i, q_data in enumerate(data["questions"]):
                q = Question(
                    test_id=test.id,
                    section_id=section_map[q_data["section_name"]],
                    question_number=i + 1,
                    statement=q_data["statement"],
                    statement_hindi=q_data["statement_hindi"],
                    correct_option_index=q_data["correct_option_index"],
                    explanation=q_data["full_explanation"],
                    explanation_hindi=q_data["full_explanation_hindi"],
                    topic=q_data["section_name"],
                    subject=q_data["paper"]
                )
                db.add(q)
                db.commit()
                
                for opt_idx, (opt_eng, opt_hin) in enumerate(zip(q_data["options"], q_data["options_hindi"])):
                    option = Option(
                        question_id=q.id,
                        option_index=opt_idx + 1,
                        text=opt_eng,
                        text_hindi=opt_hin
                    )
                    db.add(option)
                    
            db.commit()
            print("Successfully seeded the database.")
        else:
            print("Database already seeded.")
    except Exception as e:
        print(f"Error seeding DB: {e}")
    finally:
        db.close()
