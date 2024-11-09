from fastapi import FastAPI

app = FastAPI()

@app.get("/api/v1/rest/")
async def read_root():
    return {"message": "Welcome to the Blogging Service!"}
