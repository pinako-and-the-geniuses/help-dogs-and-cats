import React, { useMemo, useRef } from "react";
import ReactQuill, { Quill } from "react-quill";
import "react-quill/dist/quill.snow.css";
import axios from "axios";
import { URL, IMGURL } from "../public/config";
import ImageResize from "quill-image-resize";
Quill.register("modules/ImageResize", ImageResize);

export default function Editor(props) {
  const quillRef = useRef();

  const jwt = sessionStorage.getItem("jwt");
  const imageHandler = () => {
    console.log("에디터에서 이미지 버튼 클릭");

    // 이미지를 저장할 input type=file DOM 을 만든다
    const input = document.createElement("input");
    input.setAttribute("type", "file");
    input.setAttribute("accept", "image/*");
    input.click();

    // 이미지를 선택하면
    input.addEventListener("change", async () => {
      const file = input.files[0];
      const editor = quillRef.current.getEditor();
      const range = editor.getSelection();

      // 로딩중 표시
      editor.insertEmbed(range.index, "image", `/public/img/loading.gif`);

      //multer에 맞는 형식으로 데이터 생성
      const formData = new FormData();
      formData.append("imageFile", file);
      try {
        const res = await axios.post(`${URL}/images`, formData, {
          headers: {
            "Content-type": "multipart/form-data",
            Authorization: `Bearer ${jwt}`,
          },
        });

        const IMG_URL = `${IMGURL}${res.data.data.imageFilePath}`;
        // 정상적으로 업로드 됐다면 로딩 placeholder 삭제
        editor.deleteText(range.index, 1);

        // editor.root.innerHTML =
        //   editor.root.innerHTML + `<img src=${IMG_URL} /><br/>`; // 현재 있는 내용들 뒤에 써줘야한다.
        editor.insertEmbed(range.index, "image", IMG_URL);
      } catch (err) {
        console.log(err);
      }
    });
  };

  const modules = useMemo(() => {
    return {
      toolbar: {
        container: [
          [{ header: [1, 2, 3, 4, false] }],
          ["bold", "italic", "underline", "strike", "blockquote"],
          [
            { list: "ordered" },
            { list: "bullet" },
            { indent: "-1" },
            { indent: "+1" },
          ],
          ["link", "image"],
          [{ color: [] }],
          ["clean"],
        ],
        handlers: {
          // 이미지 처리는 우리가 직접 imageHandler라는 함수로 처리할 것이다.
          image: imageHandler,
        },
      },

      ImageResize: {
        parchment: Quill.import("parchment"),
      },
    };
  }, []);

  // 위에서 설정한 모듈들 foramts을 설정한다
  const formats = [
    "header",
    "bold",
    "italic",
    "underline",
    "strike",
    "blockquote",
    "list",
    "bullet",
    "indent",
    "link",
    "image",
    "color",
  ];

  // const onEditorChange = (value) => {
  //   setContent(value);
  // };

  return (
    <ReactQuill
      style={{ height: `${props.height}`, width: "100%" }}
      ref={quillRef}
      theme="snow"
      placeholder={props.placeholder}
      value={props.value}
      onChange={(content, source, delta, editor) =>
        props.onChange(editor.getHTML())
      }
      modules={modules}
      formats={formats}
    />
  );
}
