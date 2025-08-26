<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SportBetting-UI - GitHub README</title>
    <style>
        body {
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Noto Sans', Helvetica, Arial, sans-serif;
            line-height: 1.5;
            color: #24292f;
            background-color: #ffffff;
            margin: 0;
            padding: 20px;
            max-width: 1000px;
            margin: 0 auto;
        }

        h1 {
            font-size: 2em;
            font-weight: 600;
            margin-bottom: 16px;
            border-bottom: 1px solid #d0d7de;
            padding-bottom: 10px;
        }

        p {
            margin-bottom: 16px;
            font-size: 16px;
        }

        .repo-description {
            font-size: 18px;
            color: #656d76;
            margin-bottom: 24px;
        }

        .gif-container {
            text-align: center;
            margin: 24px 0;
            padding: 16px;
            background-color: #f6f8fa;
            border-radius: 6px;
            border: 1px solid #d0d7de;
        }

        .gif-placeholder {
            display: inline-block;
            padding: 40px 20px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border-radius: 8px;
            font-weight: 500;
            font-size: 16px;
            text-align: center;
            min-width: 300px;
            position: relative;
            overflow: hidden;
        }

        .gif-placeholder::before {
            content: '';
            position: absolute;
            top: 0;
            left: -100%;
            width: 100%;
            height: 100%;
            background: linear-gradient(90deg, transparent, rgba(255,255,255,0.2), transparent);
            animation: shimmer 2s infinite;
        }

        @keyframes shimmer {
            0% { left: -100%; }
            100% { left: 100%; }
        }

        .ufc-icon {
            font-size: 24px;
            margin-right: 8px;
        }

        .github-markdown {
            background-color: #f6f8fa;
            padding: 20px;
            border-radius: 6px;
            border: 1px solid #d0d7de;
            font-family: 'SFMono-Regular', Consolas, 'Liberation Mono', Menlo, monospace;
        }

        .markdown-title {
            font-size: 28px;
            font-weight: 600;
            margin: 0 0 16px 0;
            color: #24292f;
        }

        .markdown-description {
            font-size: 16px;
            color: #656d76;
            margin-bottom: 16px;
        }

        .uploading-text {
            color: #656d76;
            font-style: italic;
            font-size: 14px;
            margin-top: 8px;
        }
    </style>
</head>
<body>
    <div class="github-markdown">
        <h1 class="markdown-title"># SportBetting-UI</h1>
        <p class="markdown-description">A Java-based Sports Betting GUI application with robust features.(Data read from a text file)</p>
        
        <div class="gif-container">
            <div class="gif-placeholder">
                <span class="ufc-icon">🥊</span>
                Charles Oliveira Sport GIF by UFC
                <br>
                <span style="font-size: 14px; opacity: 0.8;">Loading MMA Action...</span>
            </div>
            <p class="uploading-text">![Uploading Charles Oliveira Sport GIF by UFC.gif…]()</p>
        </div>
    </div>

    <div style="margin-top: 30px; padding: 20px; background-color: #f8f9fa; border-radius: 6px; border-left: 4px solid #0969da;">
        <h3 style="margin-top: 0; color: #0969da;">Preview of your GitHub README.md</h3>
        <p style="color: #656d76; font-size: 14px;">This is how your README will appear on GitHub once the GIF upload completes. The markdown formatting will render the title, description, and embedded GIF properly in the repository view.</p>
    </div>
</body>
</html>
