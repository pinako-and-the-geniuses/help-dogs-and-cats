import React, { useMemo, useRef } from 'react';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import axios from 'axios';



function Editor(props) {
  const quillRef = useRef();

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
      try {
        // const result = await axios.post(`${URL}/images`, formData);
        const result = await axios({
          url:`${URL}/images`,
          method: "POST",
          data: {
            imageFile: formData
          },
        });
        console.log('1', formData);
        console.log('성공시 백엔드가 보내주는 데이터', result.data.url);
        const IMG_URL = result.data.url;
  
        const editor = quillRef.current.getEditor();
  
        const range = editor.getSelection();
        editor.insertEmbed(range.index, "image", IMG_URL);
  
      } catch(err){
        console.log('2', formData);
        console.log(err);
      }
    })
  } 

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