import React, { useMemo } from 'react';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import axios from 'axios';

const imageHandler = () =>{
  console.log('에디터에서 이미지 버튼 클릭');

  // 이미지를 저장할 input type=file DOM 을 만든다
  const input = document.createElement('input');
  input.setAttribute('type', 'file');
  input.setAttribute('accept','image/*');
  input.click();

  // 이미지를 선택하면
  input.addEventListener('change', async() =>{
    console.log('온체인지');
    const file = input.files[0];
    //multer에 맞는 형식으로 데이터 생성
    const formData = new FormData();
    formData.append('img', file);

  })
}

function Editor(props) {
  const modules = useMemo(() => {
    return {
      toolbar:{
        container:[
          [{ 'header': [1, 2, 3, 4, false] }],
          ['bold', 'italic', 'underline','strike', 'blockquote'],
          [{'list': 'ordered'}, {'list': 'bullet'}, {'indent': '-1'}, {'indent': '+1'}],
          ['link', 'image'],
          [{'color':[]}],
          ['clean'],
        ],
        handlers:{
          image: imageHandler,
        }
      }
    };
  },[]);

  const formats = [
      'header',
      'bold', 'italic', 'underline', 'strike', 'blockquote',
      'list', 'bullet', 'indent',
      'link', 'image', 'color'
    ]

  function handleChange(value) {
    console.log(value);
  }

  return(
      <>
        <ReactQuill 
          theme="snow"
          style={{height:"450px"}}
          modules={modules}
          formats={formats}
          onChange={handleChange}
          placeholder={props.placeholder}>
        </ReactQuill>
      </>
  )
}

export default Editor;